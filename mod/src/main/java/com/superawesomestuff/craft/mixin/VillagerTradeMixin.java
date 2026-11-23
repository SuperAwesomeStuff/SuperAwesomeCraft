package com.superawesomestuff.craft.mixin;

import com.superawesomestuff.craft.enchanting.RandomEnchantLimits;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Librarians sell Convenience books only (up to III); other Villagers sell unenchanted gear. */
@Mixin(VillagerTrade.class)
public abstract class VillagerTradeMixin {
	@Inject(method = "getOffer", at = @At("RETURN"), cancellable = true)
	private void superawesomecraft$limitTrade(LootContext lootContext, CallbackInfoReturnable<MerchantOffer> cir) {
		cir.setReturnValue(RandomEnchantLimits.limitOffer(cir.getReturnValue()));
	}
}
