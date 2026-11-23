package com.superawesomestuff.craft.enchanting;

import java.util.List;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** Pays enchanting costs straight from the player's inventory, like a recipe book. */
public final class Payment {
	private Payment() {
	}

	public static int count(Player player, Cost cost) {
		Inventory inventory = player.getInventory();
		int total = 0;
		for (int i = 0; i < inventory.getContainerSize(); i++) {
			ItemStack stack = inventory.getItem(i);
			if (cost.matches(stack)) {
				total += stack.getCount();
			}
		}
		return total;
	}

	public static boolean canAfford(Player player, List<Cost> costs) {
		if (player.hasInfiniteMaterials()) {
			return true;
		}
		for (Cost cost : costs) {
			if (count(player, cost) < cost.count()) {
				return false;
			}
		}
		return true;
	}

	/** Takes the costs if the player has all of them. Returns false and takes nothing otherwise. */
	public static boolean take(Player player, List<Cost> costs) {
		if (!canAfford(player, costs)) {
			return false;
		}
		if (player.hasInfiniteMaterials()) {
			return true;
		}
		Inventory inventory = player.getInventory();
		for (Cost cost : costs) {
			int remaining = cost.count();
			for (int i = 0; i < inventory.getContainerSize() && remaining > 0; i++) {
				ItemStack stack = inventory.getItem(i);
				if (cost.matches(stack)) {
					int taken = Math.min(remaining, stack.getCount());
					stack.shrink(taken);
					remaining -= taken;
				}
			}
		}
		inventory.setChanged();
		return true;
	}
}
