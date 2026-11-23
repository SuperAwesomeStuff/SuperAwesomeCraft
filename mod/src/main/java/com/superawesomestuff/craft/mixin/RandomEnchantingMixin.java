package com.superawesomestuff.craft.mixin;

import com.superawesomestuff.craft.enchanting.RandomEnchantLimits;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.providers.EnchantmentProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Randomly enchanted items (mob gear, enchant-with-levels loot) are capped at level III and never get Mending. */
@Mixin(EnchantmentHelper.class)
public abstract class RandomEnchantingMixin {
	@Inject(method = "enchantItem(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/item/ItemStack;ILjava/util/stream/Stream;)Lnet/minecraft/world/item/ItemStack;",
		at = @At("RETURN"))
	private static void superawesomecraft$limitRandom(RandomSource random, ItemStack itemStack, int cost, Stream<Holder<Enchantment>> source,
		CallbackInfoReturnable<ItemStack> cir) {
		RandomEnchantLimits.limit(cir.getReturnValue());
	}

	@Inject(method = "enchantItemFromProvider", at = @At("TAIL"))
	private static void superawesomecraft$limitProvider(ItemStack itemStack, RegistryAccess registryAccess, ResourceKey<EnchantmentProvider> providerKey,
		DifficultyInstance difficulty, RandomSource random, CallbackInfo ci) {
		RandomEnchantLimits.limit(itemStack);
	}
}
