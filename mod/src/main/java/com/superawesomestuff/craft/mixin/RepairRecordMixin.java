package com.superawesomestuff.craft.mixin;

import com.google.gson.JsonObject;
import com.superawesomestuff.craft.durability.MaterialRepairRecipe;
import com.superawesomestuff.craft.playtest.PlaytestLog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Writes grid repairs to the playtest log. Taking the result runs before the inputs are used up, so the grid still
 * shows what the repair cost.
 */
@Mixin(ResultSlot.class)
public abstract class RepairRecordMixin {
	@Shadow
	@Final
	private CraftingContainer craftSlots;

	@Inject(method = "onTake", at = @At("HEAD"))
	private void superawesomecraft$recordRepair(Player player, ItemStack carried, CallbackInfo ci) {
		MaterialRepairRecipe.Found repair = MaterialRepairRecipe.find(this.craftSlots.asCraftInput());
		if (repair == null) {
			return;
		}
		JsonObject data = new JsonObject();
		data.addProperty("materials", repair.materials());
		data.addProperty("damage_before", repair.tool().getDamageValue());
		data.add("item", PlaytestLog.item(carried));
		PlaytestLog.event(player, "repair", data);
	}
}
