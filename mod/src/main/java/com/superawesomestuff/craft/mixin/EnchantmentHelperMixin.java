package com.superawesomestuff.craft.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** A broken item's enchantments do nothing until it's repaired. */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
	@Inject(method = "runIterationOnItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/enchantment/EnchantmentHelper$EnchantmentVisitor;)V",
		at = @At("HEAD"), cancellable = true)
	private static void superawesomecraft$brokenHasNoEnchantments(ItemStack piece, EnchantmentHelper.EnchantmentVisitor method, CallbackInfo ci) {
		if (piece.isBroken()) {
			ci.cancel();
		}
	}

	@Inject(method = "runIterationOnItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/enchantment/EnchantmentHelper$EnchantmentInSlotVisitor;)V",
		at = @At("HEAD"), cancellable = true)
	private static void superawesomecraft$brokenHasNoEnchantmentsInSlot(ItemStack piece, EquipmentSlot slot, LivingEntity owner,
		EnchantmentHelper.EnchantmentInSlotVisitor method, CallbackInfo ci) {
		if (piece.isBroken()) {
			ci.cancel();
		}
	}
}
