package com.superawesomestuff.craft.enchanting;

import com.superawesomestuff.craft.playtest.PlaytestLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

/**
 * Beheading: each level gives a 10% chance for a player's kill to drop the victim's head, for every mob that has one.
 *
 * <p>Vanilla heads cover zombies, skeletons, wither skeletons, creepers, piglins and the dragon. If the Vanilla Tweaks
 * "More Mob Heads" data pack is installed, its head table for the mob ({@code more_mob_heads:entities/<mob>}) is rolled
 * with its random chance forced on, so every mob it covers gets the same predictable chance, with the right variant.
 * See docs/research/more-mob-heads.md.
 */
public final class Beheading {
	public static final float CHANCE_PER_LEVEL = 0.1F;
	private static final String MORE_MOB_HEADS = "more_mob_heads";
	private static final Map<EntityType<?>, Item> VANILLA_HEADS = Map.of(
		EntityTypes.ZOMBIE, Items.ZOMBIE_HEAD,
		EntityTypes.SKELETON, Items.SKELETON_SKULL,
		EntityTypes.WITHER_SKELETON, Items.WITHER_SKELETON_SKULL,
		EntityTypes.CREEPER, Items.CREEPER_HEAD,
		EntityTypes.PIGLIN, Items.PIGLIN_HEAD,
		EntityTypes.ENDER_DRAGON, Items.DRAGON_HEAD
	);
	private static final ThreadLocal<Boolean> FORCE_CHANCE = ThreadLocal.withInitial(() -> false);

	private Beheading() {
	}

	/** While true, loot random-chance conditions always pass. Used to force a pack's head roll. */
	public static boolean forcingChance() {
		return FORCE_CHANCE.get();
	}

	public static void onDrops(Holder<LootTable> table, LootContext context, List<ItemStack> drops) {
		Entity victim = context.getOptional(LootContextParams.THIS_ENTITY);
		Entity killer = context.getOptional(LootContextParams.ATTACKING_ENTITY);
		if (victim == null || !(killer instanceof Player player) || !isVictimsOwnTable(table, victim)) {
			return;
		}
		int level = ModEnchantments.level(context.getLevel().registryAccess(), player.getMainHandItem(), ModEnchantments.BEHEADING);
		if (level <= 0 || drops.stream().anyMatch(Beheading::isHead) || context.getRandom().nextFloat() >= CHANCE_PER_LEVEL * level) {
			return;
		}
		Item vanilla = VANILLA_HEADS.get(victim.getType());
		List<ItemStack> heads = vanilla != null ? List.of(new ItemStack(vanilla)) : packHead(context, victim);
		drops.addAll(heads);
		PlaytestLog.count(player, "beheading_heads", heads.size());
	}

	/** Only the victim's own loot table, so packs that nest tables can't make Beheading roll twice. */
	private static boolean isVictimsOwnTable(Holder<LootTable> table, Entity victim) {
		Optional<ResourceKey<LootTable>> own = victim.getType().getDefaultLootTable();
		return own.isPresent() && table.unwrapKey().map(own.get()::equals).orElse(false);
	}

	private static List<ItemStack> packHead(LootContext context, Entity victim) {
		Identifier type = BuiltInRegistries.ENTITY_TYPE.getKey(victim.getType());
		ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE,
			Identifier.fromNamespaceAndPath(MORE_MOB_HEADS, "entities/" + type.getPath()));
		LootTable headTable = context.getLevel().getServer().reloadableRegistries().getLootTable(key);
		List<ItemStack> heads = new ArrayList<>();
		if (headTable == LootTable.EMPTY) {
			return heads;
		}
		FORCE_CHANCE.set(true);
		try {
			headTable.getRandomItems(context, heads::add);
		} finally {
			FORCE_CHANCE.set(false);
		}
		return heads;
	}

	private static boolean isHead(ItemStack stack) {
		return stack.is(Items.PLAYER_HEAD) || VANILLA_HEADS.containsValue(stack.getItem());
	}
}
