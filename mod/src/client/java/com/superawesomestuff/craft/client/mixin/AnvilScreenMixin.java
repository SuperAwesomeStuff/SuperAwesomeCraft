package com.superawesomestuff.craft.client.mixin;

import com.superawesomestuff.craft.enchanting.EnchantTransfer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Says why moving enchantments at the anvil won't work (too few slots, wrong kind of item...), in the spot where vanilla
 * shows the XP cost. See {@link EnchantTransfer}.
 */
@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ItemCombinerScreen<AnvilMenu> {
	private static final int RED = -40864;

	private AnvilScreenMixin(AnvilMenu menu, Inventory inventory, Component title, Identifier menuResource) {
		super(menu, inventory, title, menuResource);
	}

	@Inject(method = "extractLabels", at = @At("TAIL"))
	private void superawesomecraft$transferProblem(GuiGraphicsExtractor graphics, int xm, int ym, CallbackInfo ci) {
		EnchantTransfer.Check check = EnchantTransfer.check(
			this.menu.getSlot(AnvilMenu.INPUT_SLOT).getItem(), this.menu.getSlot(AnvilMenu.ADDITIONAL_SLOT).getItem());
		Component line = switch (check.status()) {
			case OK, NOT_A_TRANSFER -> null;
			case TOO_FEW_SLOTS -> Component.translatable("superawesomecraft.anvil.transfer.too_few_slots", check.needed(), check.available());
			default -> Component.translatable("superawesomecraft.anvil.transfer." + check.status().name().toLowerCase(java.util.Locale.ROOT));
		};
		if (line != null) {
			int x = this.imageWidth - 8 - this.font.width(line) - 2;
			graphics.fill(x - 2, 67, this.imageWidth - 8, 79, 1325400064);
			graphics.text(this.font, line, x, 69, RED);
		}
	}
}
