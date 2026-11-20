package com.superawesomestuff.craft.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * No XP: the anvil only renames, for free. Combining and repairing are disabled; combining
 * enchantments moves to the new enchanting in Season 1.
 */
@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
	@Shadow
	@Final
	private DataSlot cost;

	@Shadow
	private int repairItemCountCost;

	private AnvilMenuMixin(@Nullable MenuType<?> menuType, int containerId, Inventory inventory,
			ContainerLevelAccess access, ItemCombinerMenuSlotDefinition itemInputSlots) {
		super(menuType, containerId, inventory, access, itemInputSlots);
	}

	@Inject(method = "createResult", at = @At("TAIL"))
	private void superawesomecraft$renameOnly(CallbackInfo ci) {
		if (!this.inputSlots.getItem(AnvilMenu.ADDITIONAL_SLOT).isEmpty()) {
			this.resultSlots.setItem(0, ItemStack.EMPTY);
		}

		this.cost.set(0);
		this.repairItemCountCost = 0;
		this.broadcastChanges();
	}

	@Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$freeRename(Player player, boolean hasItem, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(hasItem);
	}
}
