package com.superawesomestuff.craft.enchanting;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

/** The three one-off upgrades that add a slot to an item, one per Era. */
public enum SlotUpgrade {
	OVERWORLD("overworld", List.of(Cost.of(Items.DIAMOND_BLOCK, 1), Cost.of(Items.GOLD_BLOCK, 1))),
	NETHER("nether", List.of(Cost.of(Items.NETHER_STAR, 1))),
	END("end", List.of(Cost.of(Items.SHULKER_SHELL, 4)));

	private final String name;
	private final List<Cost> costs;

	SlotUpgrade(String name, List<Cost> costs) {
		this.name = name;
		this.costs = costs;
	}

	public int bit() {
		return 1 << this.ordinal();
	}

	public List<Cost> costs() {
		return this.costs;
	}

	public Component title() {
		return Component.translatable("superawesomecraft.enchanting.upgrade." + this.name);
	}
}
