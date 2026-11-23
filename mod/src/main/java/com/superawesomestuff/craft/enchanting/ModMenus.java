package com.superawesomestuff.craft.enchanting;

import com.superawesomestuff.craft.SuperAwesomeCraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public final class ModMenus {
	public static final MenuType<SlotEnchantingMenu> SLOT_ENCHANTING = Registry.register(
		BuiltInRegistries.MENU, SuperAwesomeCraft.id("slot_enchanting"), new MenuType<>(SlotEnchantingMenu::new, FeatureFlags.VANILLA_SET)
	);

	private ModMenus() {
	}

	public static void init() {
	}
}
