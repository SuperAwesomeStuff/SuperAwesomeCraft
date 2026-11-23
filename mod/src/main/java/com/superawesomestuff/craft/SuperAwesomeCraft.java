package com.superawesomestuff.craft;

import com.superawesomestuff.craft.enchanting.ModComponents;
import com.superawesomestuff.craft.enchanting.ModMenus;
import com.superawesomestuff.craft.enchanting.RandomEnchantLimits;
import com.superawesomestuff.craft.enchanting.ToolAbilities;
import com.superawesomestuff.craft.durability.MaterialRepairRecipe;
import com.superawesomestuff.craft.durability.RepairMaterials;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuperAwesomeCraft implements ModInitializer {
	public static final String MOD_ID = "superawesomecraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModComponents.init();
		ModMenus.init();
		RepairMaterials.init();
		RandomEnchantLimits.init();
		ToolAbilities.init();
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id("material_repair"), MaterialRepairRecipe.SERIALIZER);
		LOGGER.info("Super Awesome Craft loaded");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
