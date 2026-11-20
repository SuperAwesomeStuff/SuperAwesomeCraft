package com.superawesomestuff.craft.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * No XP: the HUD never shows the experience bar or level number. The contextual bar slot still
 * shows the locator bar and mount jump bar as in vanilla.
 */
@Mixin(Hud.class)
public class HudMixin {
	@ModifyExpressionValue(
		method = {"extractHotbarAndDecorations", "nextContextualInfoState"},
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasExperience()Z")
	)
	private boolean superawesomecraft$hideExperience(boolean original) {
		return false;
	}
}
