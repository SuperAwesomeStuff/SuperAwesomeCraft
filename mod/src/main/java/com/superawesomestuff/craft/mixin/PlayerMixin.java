package com.superawesomestuff.craft.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * No XP: players never gain or lose experience points or levels, whatever the source
 * (including the /xp command).
 */
@Mixin(Player.class)
public class PlayerMixin {
	@Inject(method = "giveExperiencePoints", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$noExperiencePoints(int amount, CallbackInfo ci) {
		ci.cancel();
	}

	@Inject(method = "giveExperienceLevels", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$noExperienceLevels(int amount, CallbackInfo ci) {
		ci.cancel();
	}
}
