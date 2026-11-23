package com.superawesomestuff.craft.client;

import com.superawesomestuff.craft.enchanting.ModMenus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.MenuScreens;

public class SuperAwesomeCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ModMenus.SLOT_ENCHANTING, SlotEnchantingScreen::new);
		ItemTooltipCallback.EVENT.register((stack, context, flag, lines) -> {
			if (stack.isBroken()) {
				lines.add(1, Component.translatable("superawesomecraft.durability.broken").withStyle(ChatFormatting.RED));
			}
		});
	}
}
