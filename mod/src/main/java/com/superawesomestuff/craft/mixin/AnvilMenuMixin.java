package com.superawesomestuff.craft.mixin;

import com.superawesomestuff.craft.playtest.PlaytestLog;
import com.superawesomestuff.craft.enchanting.BookMerging;
import com.superawesomestuff.craft.enchanting.EnchantTransfer;
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
 * No XP: the anvil renames for free, combines two enchanted books for free (vanilla's book rules, see
 * {@link BookMerging}) and moves enchantments from an item on the right to an unenchanted one of the same kind on the left
 * (see {@link EnchantTransfer}). Everything else it used to combine or repair is disabled; enchanting gear happens at the
 * table.
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

	@Inject(method = "createResult", at = @At("RETURN"))
	private void superawesomecraft$renameOnly(CallbackInfo ci) {
		ItemStack right = this.inputSlots.getItem(AnvilMenu.ADDITIONAL_SLOT);
		if (!right.isEmpty()) {
			ItemStack left = this.inputSlots.getItem(AnvilMenu.INPUT_SLOT);
			ItemStack moved = EnchantTransfer.result(left, right);
			this.resultSlots.setItem(0, moved.isEmpty() ? BookMerging.merge(left, right) : moved);
		}

		this.cost.set(0);
		this.repairItemCountCost = 0;
		this.broadcastChanges();
	}

	@Inject(method = "onTake", at = @At("HEAD"))
	private void superawesomecraft$record(Player player, ItemStack carried, CallbackInfo ci) {
		ItemStack left = this.inputSlots.getItem(AnvilMenu.INPUT_SLOT);
		ItemStack right = this.inputSlots.getItem(AnvilMenu.ADDITIONAL_SLOT);
		if (right.isEmpty()) {
			return;
		}
		com.google.gson.JsonObject data = new com.google.gson.JsonObject();
		boolean transfer = EnchantTransfer.check(left, right).ok();
		if (transfer) {
			data.add("from", PlaytestLog.item(right));
		}
		data.add("item", PlaytestLog.item(carried));
		PlaytestLog.event(player, transfer ? "transfer" : "book_merge", data);
	}

	@Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$freeRename(Player player, boolean hasItem, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(hasItem);
	}
}
