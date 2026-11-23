package com.superawesomestuff.craft.client;

import com.superawesomestuff.craft.enchanting.ModMenus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class SuperAwesomeCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ModMenus.SLOT_ENCHANTING, SlotEnchantingScreen::new);
	}
}
