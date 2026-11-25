package com.superawesomestuff.craft.durability;

import java.util.Map;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Repairable;

/**
 * Repair materials for gear vanilla gives none (or an odd one), so everything with durability can be repaired in the
 * crafting grid and says so in its tooltip.
 */
public final class RepairMaterials {
	private static final Map<Item, Item> MATERIALS = Map.ofEntries(
		Map.entry(Items.BOW, Items.STRING),
		Map.entry(Items.CROSSBOW, Items.STRING),
		Map.entry(Items.FISHING_ROD, Items.STRING),
		Map.entry(Items.TRIDENT, Items.PRISMARINE_SHARD),
		Map.entry(Items.SHIELD, Items.IRON_INGOT),
		Map.entry(Items.SHEARS, Items.IRON_INGOT),
		Map.entry(Items.FLINT_AND_STEEL, Items.IRON_INGOT),
		Map.entry(Items.BRUSH, Items.COPPER_INGOT),
		Map.entry(Items.CARROT_ON_A_STICK, Items.CARROT),
		Map.entry(Items.WARPED_FUNGUS_ON_A_STICK, Items.WARPED_FUNGUS),
		// Vanilla uses Phantom Membrane; ours keeps the Elytra tied to the End.
		Map.entry(Items.ELYTRA, Items.POPPED_CHORUS_FRUIT)
	);

	private RepairMaterials() {
	}

	public static void init() {
		DefaultItemComponentEvents.MODIFY.register(context -> MATERIALS.forEach((item, material) ->
			context.modify(item, builder -> builder.set(DataComponents.REPAIRABLE,
				new Repairable(HolderSet.direct(BuiltInRegistries.ITEM.wrapAsHolder(material)))))));
	}
}
