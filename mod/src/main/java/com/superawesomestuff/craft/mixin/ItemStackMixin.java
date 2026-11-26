package com.superawesomestuff.craft.mixin;

import com.superawesomestuff.craft.playtest.PlaytestLog;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.function.TriConsumer;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Durability that's never a big deal (docs/season-1/enchanting/README.md#durability): gear never breaks. At 0 it
 * stays in the inventory but stops working (no stats, no enchantment effects, no use) until it's repaired.
 */
@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Shadow
	public abstract boolean isBroken();

	@Shadow
	public abstract int getMaxDamage();

	@Shadow
	public abstract void setDamageValue(int value);

	@Shadow
	public abstract int getDamageValue();

	@Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$neverBreak(int newDamage, @Nullable ServerPlayer player, Consumer<ItemStack> onBreak, CallbackInfo ci) {
		if (newDamage >= this.getMaxDamage()) {
			boolean wasWorking = this.getDamageValue() < this.getMaxDamage();
			this.setDamageValue(this.getMaxDamage());
			if (player != null && wasWorking) {
				com.google.gson.JsonObject data = new com.google.gson.JsonObject();
				data.add("item", PlaytestLog.item((ItemStack) (Object) this));
				PlaytestLog.event(player, "gear_broken", data);
			}
			ci.cancel();
		}
	}

	@Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$brokenMinesLikeAHand(BlockState state, CallbackInfoReturnable<Float> cir) {
		if (this.isBroken()) {
			cir.setReturnValue(1.0F);
		}
	}

	@Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$brokenHarvestsNothing(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (this.isBroken()) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "forEachModifier(Lnet/minecraft/world/entity/EquipmentSlotGroup;Lorg/apache/commons/lang3/function/TriConsumer;)V",
		at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$brokenHasNoStats(EquipmentSlotGroup slot,
		TriConsumer<Holder<Attribute>, AttributeModifier, ItemAttributeModifiers.Display> consumer, CallbackInfo ci) {
		if (this.isBroken()) {
			ci.cancel();
		}
	}

	@Inject(method = "forEachModifier(Lnet/minecraft/world/entity/EquipmentSlot;Ljava/util/function/BiConsumer;)V",
		at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$brokenHasNoStatsInSlot(EquipmentSlot slot, BiConsumer<Holder<Attribute>, AttributeModifier> consumer, CallbackInfo ci) {
		if (this.isBroken()) {
			ci.cancel();
		}
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$brokenCannotBeUsed(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		if (this.isBroken()) {
			cir.setReturnValue(InteractionResult.FAIL);
		}
	}

	@Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$brokenCannotBeUsedOnBlocks(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		if (this.isBroken()) {
			cir.setReturnValue(InteractionResult.FAIL);
		}
	}
}
