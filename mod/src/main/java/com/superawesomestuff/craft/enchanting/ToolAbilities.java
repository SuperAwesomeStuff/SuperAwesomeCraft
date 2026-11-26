package com.superawesomestuff.craft.enchanting;

import com.superawesomestuff.craft.playtest.PlaytestLog;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

/**
 * Season 1 tool enchantments with Java behaviour: Excavating and Harvesting (3x3), Felling (whole trees), Smelting (smelted drops),
 * Magnetism (drops straight to the inventory) and Beheading (see {@link Beheading}). Area abilities break each extra block through the normal break path, so every block costs
 * durability and drops normally. Crouching turns Excavating and Felling off.
 */
public final class ToolAbilities {
	public static final float EXCAVATING_SPEED = 0.3F;
	public static final int FELLING_LIMIT = 192;
	private static final ThreadLocal<Boolean> BREAKING_EXTRA = ThreadLocal.withInitial(() -> false);
	private static final ThreadLocal<Direction> HIT_FACE = new ThreadLocal<>();

	private ToolAbilities() {
	}

	public static void init() {
		PlayerBlockBreakEvents.BEFORE.register((level, player, pos, state, blockEntity) -> {
			HIT_FACE.set(hitFace(player, pos));
			return true;
		});
		PlayerBlockBreakEvents.AFTER.register(ToolAbilities::afterBreak);
		LootTableEvents.MODIFY_DROPS.register((table, context, drops) -> {
			smelt(context, drops);
			Beheading.onDrops(table, context, drops);
			magnetise(context, drops);
		});
	}

	/**
	 * Excavating's mining speed: slower, except when the tool would mine the block instantly anyway (vanilla instant-mines
	 * when speed reaches 30 times the block's hardness), so Efficiency V with Haste II still instant-mines stone.
	 */
	public static float excavatingSpeed(float speed, float hardness) {
		return speed >= hardness * 30.0F ? speed : speed * EXCAVATING_SPEED;
	}

	/**
	 * True when the player's tool digs 3x3 and is digging a block it's good at, so the 3x3 (and its slowdown) applies.
	 * Excavating does this on picks and shovels, Harvesting on hoes.
	 */
	public static boolean excavates(Player player, BlockState state) {
		ItemStack tool = player.getMainHandItem();
		return !player.isShiftKeyDown() && digsArea(player.level().registryAccess(), tool) && tool.isCorrectToolForDrops(state);
	}

	/** Excavating, or Harvesting on a hoe: the tool mines a 3x3. */
	public static boolean digsArea(net.minecraft.core.HolderLookup.Provider registries, ItemStack tool) {
		return ModEnchantments.level(registries, tool, ModEnchantments.EXCAVATING) > 0
			|| (tool.is(net.minecraft.tags.ItemTags.HOES) && ModEnchantments.level(registries, tool, ModEnchantments.HARVESTING) > 0);
	}

	/** The face of {@code pos} the player is looking at, from a fresh ray cast. Falls back to their look direction. */
	private static Direction hitFace(Player player, BlockPos pos) {
		net.minecraft.world.phys.HitResult hit = player.pick(player.blockInteractionRange() + 1.0, 1.0F, false);
		if (hit instanceof net.minecraft.world.phys.BlockHitResult blockHit && blockHit.getBlockPos().equals(pos)) {
			return blockHit.getDirection();
		}
		return Math.abs(player.getXRot()) > 45 ? Direction.UP : player.getDirection();
	}

	private static void afterBreak(Level level, Player player, BlockPos pos, BlockState state, net.minecraft.world.level.block.entity.BlockEntity blockEntity) {
		if (BREAKING_EXTRA.get() || !(player instanceof ServerPlayer serverPlayer) || player.isShiftKeyDown()) {
			return;
		}
		ItemStack tool = player.getMainHandItem();
		List<BlockPos> extra = new ArrayList<>();
		if (digsArea(level.registryAccess(), tool)) {
			extra = excavationArea(player, pos, state, level, tool);
		} else if (ModEnchantments.level(level.registryAccess(), tool, ModEnchantments.FELLING) > 0 && state.is(BlockTags.LOGS)) {
			extra = connectedLogs(level, pos);
		}
		if (extra.isEmpty()) {
			return;
		}
		String ability = digsArea(level.registryAccess(), tool)
			? (ModEnchantments.level(level.registryAccess(), tool, ModEnchantments.EXCAVATING) > 0 ? "excavating_blocks" : "harvesting_blocks")
			: "felling_logs";
		int broken = 0;
		BREAKING_EXTRA.set(true);
		try {
			for (BlockPos target : extra) {
				if (tool.isEmpty() || tool.isBroken() || player.getMainHandItem() != tool) {
					break;
				}
				if (serverPlayer.gameMode.destroyBlock(target)) {
					broken++;
				}
			}
		} finally {
			BREAKING_EXTRA.set(false);
		}
		PlaytestLog.count(serverPlayer, ability, broken);
	}

	/** The other eight blocks of a 3x3 facing the player, limited to blocks the tool mines and no harder than the first. */
	private static List<BlockPos> excavationArea(Player player, BlockPos pos, BlockState origin, Level level, ItemStack tool) {
		if (!tool.isCorrectToolForDrops(origin)) {
			return List.of();
		}
		Direction facing = HIT_FACE.get() != null ? HIT_FACE.get() : Direction.UP;
		float originHardness = origin.getDestroySpeed(level, pos);
		List<BlockPos> result = new ArrayList<>();
		for (int a = -1; a <= 1; a++) {
			for (int b = -1; b <= 1; b++) {
				if (a == 0 && b == 0) {
					continue;
				}
				BlockPos target = switch (facing.getAxis()) {
					case Y -> pos.offset(a, 0, b);
					case X -> pos.offset(0, a, b);
					case Z -> pos.offset(a, b, 0);
				};
				BlockState state = level.getBlockState(target);
				float hardness = state.getDestroySpeed(level, target);
				if (!state.isAir() && hardness >= 0 && hardness <= originHardness + 0.5F && tool.isCorrectToolForDrops(state)
					&& tool.getDestroySpeed(state) > 1.0F) {
					result.add(target);
				}
			}
		}
		return result;
	}

	private static List<BlockPos> connectedLogs(Level level, BlockPos start) {
		List<BlockPos> result = new ArrayList<>();
		Set<BlockPos> seen = new HashSet<>();
		ArrayDeque<BlockPos> queue = new ArrayDeque<>();
		seen.add(start);
		queue.add(start);
		while (!queue.isEmpty() && result.size() < FELLING_LIMIT) {
			BlockPos current = queue.poll();
			for (BlockPos next : BlockPos.betweenClosed(current.offset(-1, 0, -1), current.offset(1, 1, 1))) {
				BlockPos immutable = next.immutable();
				if (seen.add(immutable) && level.getBlockState(immutable).is(BlockTags.LOGS)) {
					result.add(immutable);
					queue.add(immutable);
				}
			}
		}
		return result;
	}

	/** Magnetism: drops from blocks you mine and mobs you kill go into your inventory. What doesn't fit drops as normal. */
	private static void magnetise(LootContext context, List<ItemStack> drops) {
		ServerPlayer player = null;
		ItemInstance tool = null;
		if (context.hasParameter(LootContextParams.BLOCK_STATE)
			&& context.getOptional(LootContextParams.THIS_ENTITY) instanceof ServerPlayer breaker) {
			player = breaker;
			tool = context.getOptional(LootContextParams.TOOL);
		} else if (context.getOptional(LootContextParams.DAMAGE_SOURCE) instanceof net.minecraft.world.damagesource.DamageSource source
			&& source.getEntity() instanceof ServerPlayer killer && source.getDirectEntity() == killer) {
			player = killer;
			tool = source.getWeaponItem();
		}
		if (player == null || tool == null || ModEnchantments.level(context.getLevel().registryAccess(), tool, ModEnchantments.MAGNETISM) <= 0) {
			return;
		}
		int collected = 0;
		for (ListIterator<ItemStack> it = drops.listIterator(); it.hasNext(); ) {
			ItemStack drop = it.next();
			int before = drop.getCount();
			player.getInventory().add(drop);
			collected += before - drop.getCount();
			if (drop.isEmpty()) {
				it.remove();
			}
		}
		PlaytestLog.count(player, "magnetism_items", collected);
	}

	private static void smelt(LootContext context, List<ItemStack> drops) {
		ItemInstance tool = context.getOptional(LootContextParams.TOOL);
		if (tool == null || !context.hasParameter(LootContextParams.BLOCK_STATE)) {
			return;
		}
		ServerLevel level = context.getLevel();
		if (ModEnchantments.level(level.registryAccess(), tool, ModEnchantments.SMELTING) <= 0) {
			return;
		}
		for (ListIterator<ItemStack> it = drops.listIterator(); it.hasNext(); ) {
			ItemStack drop = it.next();
			SingleRecipeInput input = new SingleRecipeInput(drop);
			Optional<RecipeHolder<SmeltingRecipe>> recipe = level.recipeAccess().getRecipeFor(RecipeType.SMELTING, input, level);
			if (recipe.isPresent()) {
				ItemStack result = recipe.get().value().assemble(input);
				if (!result.isEmpty()) {
					result.setCount(result.getCount() * drop.getCount());
					it.set(result);
					if (context.getOptional(LootContextParams.THIS_ENTITY) instanceof Player player) {
						PlaytestLog.count(player, "smelting_items", result.getCount());
					}
				}
			}
		}
	}
}
