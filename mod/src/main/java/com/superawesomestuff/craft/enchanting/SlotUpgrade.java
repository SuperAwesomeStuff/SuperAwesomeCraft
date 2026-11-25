package com.superawesomestuff.craft.enchanting;

import java.util.List;
import net.minecraft.network.chat.Component;

/** The three one-off upgrades that add a slot to an item, one per Era. */
public enum SlotUpgrade {
	OVERWORLD("overworld"),
	NETHER("nether"),
	END("end");

	private final String name;

	SlotUpgrade(String name) {
		this.name = name;
	}

	/** The name used in the config file and translation keys. */
	public String key() {
		return this.name;
	}

	public int bit() {
		return 1 << this.ordinal();
	}

	/** What this upgrade costs, from the enchanting recipes config. */
	public List<Cost> costs() {
		return EnchantingRecipes.upgradeCosts(this);
	}

	public Component title() {
		return Component.translatable("superawesomecraft.enchanting.upgrade." + this.name);
	}
}
