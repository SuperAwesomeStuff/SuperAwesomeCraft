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
	private static final Map<Item, Item> MATERIALS = Map.of(
		Items.BOW, Items.STRING,
		Items.CROSSBOW, Items.STRING,
		Items.FISHING_ROD, Items.STRING,
		Items.TRIDENT, Items.PRISMARINE_SHARD,
		Items.SHIELD, Items.IRON_INGOT,
		Items.SHEARS, Items.IRON_INGOT,
		Items.FLINT_AND_STEEL, Items.IRON_INGOT,
		Items.BRUSH, Items.COPPER_INGOT,
		Items.CARROT_ON_A_STICK, Items.CARROT,
		Items.WARPED_FUNGUS_ON_A_STICK, Items.WARPED_FUNGUS
	);

	private RepairMaterials() {
	}

	public static void init() {
		DefaultItemComponentEvents.MODIFY.register(context -> MATERIALS.forEach((item, material) ->
			context.modify(item, builder -> builder.set(DataComponents.REPAIRABLE,
				new Repairable(HolderSet.direct(BuiltInRegistries.ITEM.wrapAsHolder(material)))))));
	}
}
