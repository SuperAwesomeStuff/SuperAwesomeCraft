package com.superawesomestuff.craft.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * No XP: experience orbs never enter the world.
 *
 * <p>Every vanilla source (mob and player deaths, ores, furnaces, breeding, fishing, trading,
 * grindstones, Bottles o' Enchanting, the Dragon) spawns orbs through this method. Mobs keep
 * their hidden XP value, and Sculk Catalysts read it directly rather than from orbs, so sculk
 * spreads exactly as in vanilla.
 */
@Mixin(ServerLevel.class)
public class ServerLevelMixin {
	@Inject(method = "addFreshEntity", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$blockExperienceOrbs(Entity entity, CallbackInfoReturnable<Boolean> cir) {
		if (entity instanceof ExperienceOrb) {
			cir.setReturnValue(false);
		}
	}
}
