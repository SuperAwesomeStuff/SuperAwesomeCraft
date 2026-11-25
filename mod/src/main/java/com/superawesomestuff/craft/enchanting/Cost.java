package com.superawesomestuff.craft.enchanting;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/**
 * A number of one material: a single item, or any item in a tag (shown as {@code display}). {@code step} is how many more
 * each level after the first costs: a cost of 16 with a step of 16 is 16, 32, 48... for levels I, II, III.
 */
public record Cost(Item display, @Nullable TagKey<Item> tag, int count, int step) {
	public static Cost of(Item item, int count) {
		return new Cost(item, null, count, 0);
	}

	public static Cost of(TagKey<Item> tag, Item display, int count) {
		return new Cost(display, tag, count, 0);
	}

	/** {@code count} for level I, plus {@code step} more for each level after. */
	public static Cost rising(Item item, int count, int step) {
		return new Cost(item, null, count, step);
	}

	public boolean matches(ItemStack stack) {
		return this.tag != null ? stack.is(this.tag) : stack.is(this.display);
	}

	/** What this costs for one specific level (1 is level I), as a fixed amount. */
	public Cost atLevel(int level) {
		return new Cost(this.display, this.tag, this.count + this.step * Math.max(0, level - 1), 0);
	}
}
