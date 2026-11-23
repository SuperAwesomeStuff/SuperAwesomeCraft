package com.superawesomestuff.craft.enchanting;

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
 * Season 1 tool enchantments with Java behaviour: Excavating (3x3), Felling (whole trees), Smelting (smelted drops) and
 * Beheading (mob heads). Area abilities break each extra block through the normal break path, so every block costs
 * durability and drops normally. Crouching turns Excavating and Felling off.
 */
public final class ToolAbilities {
	public static final float EXCAVATING_SPEED = 0.3F;
	public static final int FELLING_LIMIT = 192;
	public static final float BEHEADING_CHANCE_PER_LEVEL = 0.1F;
	private static final Map<EntityType<?>, net.minecraft.world.item.Item> HEADS = Map.of(
		EntityTypes.ZOMBIE, Items.ZOMBIE_HEAD,
		EntityTypes.SKELETON, Items.SKELETON_SKULL,
		EntityTypes.CREEPER, Items.CREEPER_HEAD,
		EntityTypes.PIGLIN, Items.PIGLIN_HEAD
	);
	private static final ThreadLocal<Boolean> BREAKING_EXTRA = ThreadLocal.withInitial(() -> false);

	private ToolAbilities() {
	}

	public static void init() {
		PlayerBlockBreakEvents.AFTER.register(ToolAbilities::afterBreak);
		LootTableEvents.MODIFY_DROPS.register((table, context, drops) -> {
			smelt(context, drops);
			behead(context, drops);
		});
	}

	public static boolean excavates(Player player) {
		return !player.isShiftKeyDown()
			&& ModEnchantments.level(player.level().registryAccess(), player.getMainHandItem(), ModEnchantments.EXCAVATING) > 0;
	}

	private static void afterBreak(Level level, Player player, BlockPos pos, BlockState state, net.minecraft.world.level.block.entity.BlockEntity blockEntity) {
		if (BREAKING_EXTRA.get() || !(player instanceof ServerPlayer serverPlayer) || player.isShiftKeyDown()) {
			return;
		}
		ItemStack tool = player.getMainHandItem();
		List<BlockPos> extra = new ArrayList<>();
		if (ModEnchantments.level(level.registryAccess(), tool, ModEnchantments.EXCAVATING) > 0) {
			extra = excavationArea(player, pos, state, level, tool);
		} else if (ModEnchantments.level(level.registryAccess(), tool, ModEnchantments.FELLING) > 0 && state.is(BlockTags.LOGS)) {
			extra = connectedLogs(level, pos);
		}
		if (extra.isEmpty()) {
			return;
		}
		BREAKING_EXTRA.set(true);
		try {
			for (BlockPos target : extra) {
				if (tool.isEmpty() || tool.isBroken() || player.getMainHandItem() != tool) {
					break;
				}
				serverPlayer.gameMode.destroyBlock(target);
			}
		} finally {
			BREAKING_EXTRA.set(false);
		}
	}

	/** The other eight blocks of a 3x3 facing the player, limited to blocks the tool mines and no harder than the first. */
	private static List<BlockPos> excavationArea(Player player, BlockPos pos, BlockState origin, Level level, ItemStack tool) {
		if (!tool.isCorrectToolForDrops(origin) && origin.getDestroySpeed(level, pos) > 0) {
			return List.of();
		}
		Direction facing = Math.abs(player.getXRot()) > 45 ? Direction.UP : player.getDirection();
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
				}
			}
		}
	}

	private static void behead(LootContext context, List<ItemStack> drops) {
		Entity victim = context.getOptional(LootContextParams.THIS_ENTITY);
		Entity killer = context.getOptional(LootContextParams.ATTACKING_ENTITY);
		if (victim == null || !(killer instanceof LivingEntity living)) {
			return;
		}
		net.minecraft.world.item.Item head = HEADS.get(victim.getType());
		if (head == null) {
			return;
		}
		int level = ModEnchantments.level(context.getLevel().registryAccess(), living.getMainHandItem(), ModEnchantments.BEHEADING);
		if (level > 0 && context.getRandom().nextFloat() < BEHEADING_CHANCE_PER_LEVEL * level) {
			drops.add(new ItemStack(head));
		}
	}
}
