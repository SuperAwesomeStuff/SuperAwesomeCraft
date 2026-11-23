package com.superawesomestuff.craft.mixin;

import com.superawesomestuff.craft.enchanting.Beheading;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Lets Beheading roll a head table with its random chance forced on. Has no effect at any other time. */
@Mixin(LootItemRandomChanceCondition.class)
public abstract class RandomChanceMixin {
	@Inject(method = "test", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$forced(LootContext context, CallbackInfoReturnable<Boolean> cir) {
		if (Beheading.forcingChance()) {
			cir.setReturnValue(true);
		}
	}
}
