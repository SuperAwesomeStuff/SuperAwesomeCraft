package com.superawesomestuff.craft.enchanting;

import java.util.List;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Harvesting, on hoes: right-clicking a ripe crop harvests it and replants it with one of its own seeds, and tilling
 * tills the 3x3 around the block you clicked. Crouch to till a single block.
 */
public final class Harvesting {
	private static final ThreadLocal<Boolean> TILLING_EXTRA = ThreadLocal.withInitial(() -> false);

	private Harvesting() {
	}

	public static void init() {
		UseBlockCallback.EVENT.register(Harvesting::onUse);
	}

	private static InteractionResult onUse(Player player, Level level, InteractionHand hand, BlockHitResult hit) {
		ItemStack hoe = player.getItemInHand(hand);
		if (player.isSpectator() || TILLING_EXTRA.get()
			|| ModEnchantments.level(level.registryAccess(), hoe, ModEnchantments.HARVESTING) <= 0) {
			return InteractionResult.PASS;
		}
		BlockPos pos = hit.getBlockPos();
		BlockState state = level.getBlockState(pos);
		if (state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state)) {
			if (level instanceof ServerLevel serverLevel) {
				harvest(serverLevel, player, pos, state, hoe, hand);
			}
			return InteractionResult.SUCCESS;
		}
		if (!player.isShiftKeyDown() && level instanceof ServerLevel) {
			tillAround(player, hand, hit);
		}
		// The clicked block itself is tilled by the hoe as normal.
		return InteractionResult.PASS;
	}

	/** Harvests a ripe crop and replants it, keeping back one seed from its drops. */
	public static void harvest(ServerLevel level, Player player, BlockPos pos, BlockState state, ItemStack hoe, InteractionHand hand) {
		CropBlock crop = (CropBlock) state.getBlock();
		List<ItemStack> drops = Block.getDrops(state, level, pos, null, player, hoe);
		for (ItemStack drop : drops) {
			if (drop.is(state.getBlock().asItem())) {
				drop.shrink(1);
				break;
			}
		}
		level.setBlock(pos, crop.getStateForAge(0), Block.UPDATE_ALL);
		for (ItemStack drop : drops) {
			if (!drop.isEmpty()) {
				Block.popResource(level, pos, drop);
			}
		}
		hoe.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
	}

	/** Uses the hoe on the eight blocks around the clicked one, on the clicked face's plane. */
	private static void tillAround(Player player, InteractionHand hand, BlockHitResult hit) {
		Direction face = hit.getDirection();
		if (face == Direction.DOWN) {
			return;
		}
		BlockPos center = hit.getBlockPos();
		TILLING_EXTRA.set(true);
		try {
			for (int dx = -1; dx <= 1; dx++) {
				for (int dz = -1; dz <= 1; dz++) {
					if (dx == 0 && dz == 0) {
						continue;
					}
					ItemStack hoe = player.getItemInHand(hand);
					if (hoe.isEmpty() || hoe.isBroken()) {
						return;
					}
					BlockPos target = center.offset(dx, 0, dz);
					BlockHitResult targetHit = new BlockHitResult(Vec3.atCenterOf(target).relative(face, 0.5), face, target, false);
					hoe.useOn(new UseOnContext(player, hand, targetHit));
				}
			}
		} finally {
			TILLING_EXTRA.set(false);
		}
	}
}
