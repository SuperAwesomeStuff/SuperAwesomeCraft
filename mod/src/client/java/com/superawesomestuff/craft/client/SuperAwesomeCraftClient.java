package com.superawesomestuff.craft.client;

import com.superawesomestuff.craft.SuperAwesomeCraft;
import com.superawesomestuff.craft.enchanting.EnchantingRecipes;
import com.superawesomestuff.craft.enchanting.EnchantingRecipesSync;
import com.superawesomestuff.craft.enchanting.ModMenus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.MenuScreens;

public class SuperAwesomeCraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ModMenus.SLOT_ENCHANTING, SlotEnchantingScreen::new);
		// The server's enchanting costs, so the screen matches what it charges.
		ClientPlayNetworking.registerGlobalReceiver(EnchantingRecipesSync.Payload.TYPE, (payload, context) -> {
			try {
				EnchantingRecipes.install(EnchantingRecipes.fromJson(payload.json()));
			} catch (RuntimeException e) {
				SuperAwesomeCraft.LOGGER.error("Couldn't read the server's enchanting recipes", e);
			}
		});
		ItemTooltipCallback.EVENT.register((stack, context, flag, lines) -> {
			if (stack.isBroken()) {
				lines.add(1, Component.translatable("superawesomecraft.durability.broken").withStyle(ChatFormatting.RED));
			}
			Component repair = RepairTooltip.describe(stack);
			if (repair != null) {
				lines.add(Math.min(lines.size(), stack.isBroken() ? 2 : 1), repair);
			}
		});
	}
}
