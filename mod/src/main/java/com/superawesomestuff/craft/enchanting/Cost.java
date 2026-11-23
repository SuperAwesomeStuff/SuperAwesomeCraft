package com.superawesomestuff.craft.enchanting;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/** A number of one material: a single item, or any item in a tag (shown as {@code display}). */
public record Cost(Item display, @Nullable TagKey<Item> tag, int count) {
	public static Cost of(Item item, int count) {
		return new Cost(item, null, count);
	}

	public static Cost of(TagKey<Item> tag, Item display, int count) {
		return new Cost(display, tag, count);
	}

	public boolean matches(ItemStack stack) {
		return this.tag != null ? stack.is(this.tag) : stack.is(this.display);
	}

	public Cost times(int factor) {
		return new Cost(this.display, this.tag, this.count * factor);
	}
}
