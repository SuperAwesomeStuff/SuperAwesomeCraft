package com.superawesomestuff.craft.enchanting;

import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Pays enchanting costs from the table's material slots ({@code first} to {@code last}, inclusive). Creative players
 * pay nothing.
 */
public final class Payment {
	private Payment() {
	}

	public static int count(Container container, int first, int last, Cost cost) {
		int total = 0;
		for (int i = first; i <= last; i++) {
			ItemStack stack = container.getItem(i);
			if (cost.matches(stack)) {
				total += stack.getCount();
			}
		}
		return total;
	}

	public static boolean canAfford(Player player, Container container, int first, int last, List<Cost> costs) {
		if (player.hasInfiniteMaterials()) {
			return true;
		}
		for (Cost cost : costs) {
			if (count(container, first, last, cost) < cost.count()) {
				return false;
			}
		}
		return true;
	}

	/** Takes the costs if the slots hold all of them. Returns false and takes nothing otherwise. */
	public static boolean take(Player player, Container container, int first, int last, List<Cost> costs) {
		if (!canAfford(player, container, first, last, costs)) {
			return false;
		}
		if (player.hasInfiniteMaterials()) {
			return true;
		}
		for (Cost cost : costs) {
			int remaining = cost.count();
			for (int i = first; i <= last && remaining > 0; i++) {
				ItemStack stack = container.getItem(i);
				if (cost.matches(stack)) {
					int taken = Math.min(remaining, stack.getCount());
					stack.shrink(taken);
					remaining -= taken;
				}
			}
		}
		container.setChanged();
		return true;
	}
}
