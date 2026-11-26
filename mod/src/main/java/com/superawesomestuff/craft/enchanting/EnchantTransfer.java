package com.superawesomestuff.craft.enchanting;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/**
 * Moving enchantments to a new item at the anvil (docs/season-1/enchanting/README.md#moving-enchantments), so enchanting
 * early isn't wasted when you move up a tier. As in vanilla, the item being worked on goes on the left: an unenchanted
 * item, and on the right the enchanted item of the same kind in any material. Every enchantment, curses included, and
 * every slot upgrade moves to the left item. It's free, and the right item is used up. If the enchantments don't fit the
 * left item's slots, nothing happens.
 */
public final class EnchantTransfer {
	/** Kinds of item that can swap enchantments across materials. Any other item only matches itself. */
	private static final List<TagKey<Item>> KINDS = List.of(
		ItemTags.PICKAXES, ItemTags.AXES, ItemTags.SHOVELS, ItemTags.HOES, ItemTags.SWORDS, ItemTags.SPEARS,
		ItemTags.HEAD_ARMOR, ItemTags.CHEST_ARMOR, ItemTags.LEG_ARMOR, ItemTags.FOOT_ARMOR
	);

	public enum Status {
		OK,
		/** Not a transfer at all (books, stacks, nothing enchanted on the right). The anvil does something else. */
		NOT_A_TRANSFER,
		DIFFERENT_KIND,
		TARGET_ENCHANTED,
		UNSUPPORTED,
		TOO_FEW_SLOTS
	}

	/** Whether {@code target} can take {@code source}'s enchantments, and the slots needed and available. */
	public record Check(Status status, int needed, int available) {
		public boolean ok() {
			return this.status == Status.OK;
		}
	}

	private EnchantTransfer() {
	}

	public static Check check(ItemStack target, ItemStack source) {
		ItemEnchantments moving = EnchantRules.enchantments(source);
		if (target.isEmpty() || source.isEmpty() || target.getCount() != 1 || source.getCount() != 1
			|| isBook(target) || isBook(source) || moving.isEmpty()) {
			return new Check(Status.NOT_A_TRANSFER, 0, 0);
		}
		if (!sameKind(target, source)) {
			return new Check(Status.DIFFERENT_KIND, 0, 0);
		}
		if (!EnchantRules.enchantments(target).isEmpty()) {
			return new Check(Status.TARGET_ENCHANTED, 0, 0);
		}
		for (Holder<Enchantment> enchantment : moving.keySet()) {
			if (!enchantment.value().isSupportedItem(target)) {
				return new Check(Status.UNSUPPORTED, 0, 0);
			}
		}
		int needed = EnchantRules.usedSlots(source);
		int available = EnchantRules.baseSlots(target) + Integer.bitCount(upgrades(target, source));
		return new Check(needed <= available ? Status.OK : Status.TOO_FEW_SLOTS, needed, available);
	}

	/** {@code target} with {@code source}'s enchantments and slot upgrades, or empty if the transfer isn't allowed. */
	public static ItemStack result(ItemStack target, ItemStack source) {
		if (!check(target, source).ok()) {
			return ItemStack.EMPTY;
		}
		ItemStack out = target.copy();
		out.set(DataComponents.ENCHANTMENTS, EnchantRules.enchantments(source));
		int upgrades = upgrades(target, source);
		if (upgrades != 0) {
			out.set(com.superawesomestuff.craft.enchanting.ModComponents.SLOT_UPGRADES, upgrades);
		}
		return out;
	}

	public static boolean sameKind(ItemStack a, ItemStack b) {
		if (a.is(b.getItem())) {
			return true;
		}
		for (TagKey<Item> kind : KINDS) {
			if (a.is(kind) && b.is(kind)) {
				return true;
			}
		}
		return false;
	}

	private static int upgrades(ItemStack target, ItemStack source) {
		return EnchantRules.upgradeBits(target) | EnchantRules.upgradeBits(source);
	}

	private static boolean isBook(ItemStack stack) {
		return stack.is(Items.BOOK) || stack.is(Items.ENCHANTED_BOOK);
	}
}
