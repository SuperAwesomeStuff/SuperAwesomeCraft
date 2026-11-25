package com.superawesomestuff.craft.enchanting;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/**
 * Combining two enchanted books at the anvil, for free, with vanilla's rules: two equal levels make the next level (up to
 * the enchantment's max), different levels keep the higher, and an enchantment that conflicts with one already on the
 * left book is left out.
 */
public final class BookMerging {
	private BookMerging() {
	}

	/** The combined book, or empty if these aren't two enchanted books or combining changes nothing. */
	public static ItemStack merge(ItemStack left, ItemStack right) {
		if (!left.is(Items.ENCHANTED_BOOK) || !right.is(Items.ENCHANTED_BOOK) || left.getCount() != 1) {
			return ItemStack.EMPTY;
		}
		ItemEnchantments base = left.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
		ItemEnchantments added = right.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
		ItemEnchantments.Mutable result = new ItemEnchantments.Mutable(base);
		for (var entry : added.entrySet()) {
			Holder<Enchantment> enchantment = entry.getKey();
			int current = result.getLevel(enchantment);
			int incoming = entry.getIntValue();
			boolean conflicts = result.keySet().stream()
				.anyMatch(other -> !other.equals(enchantment) && !Enchantment.areCompatible(enchantment, other));
			if (conflicts) {
				continue;
			}
			int level = current == incoming ? incoming + 1 : Math.max(current, incoming);
			result.set(enchantment, Math.min(level, enchantment.value().getMaxLevel()));
		}
		ItemEnchantments merged = result.toImmutable();
		if (merged.equals(base)) {
			return ItemStack.EMPTY;
		}
		ItemStack out = left.copy();
		out.set(DataComponents.STORED_ENCHANTMENTS, merged);
		return out;
	}
}
