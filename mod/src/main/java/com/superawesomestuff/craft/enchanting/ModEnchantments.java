package com.superawesomestuff.craft.enchanting;

import com.superawesomestuff.craft.SuperAwesomeCraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/** Keys for the Season 1 enchantments whose behaviour lives in Java. Their definitions are data files. */
public final class ModEnchantments {
	public static final ResourceKey<Enchantment> EXCAVATING = key("excavating");
	public static final ResourceKey<Enchantment> FELLING = key("felling");
	public static final ResourceKey<Enchantment> SMELTING = key("smelting");
	public static final ResourceKey<Enchantment> BEHEADING = key("beheading");
	public static final ResourceKey<Enchantment> MAGNETISM = key("magnetism");
	public static final ResourceKey<Enchantment> HARVESTING = key("harvesting");
	public static final ResourceKey<Enchantment> BULWARK = key("bulwark");

	private ModEnchantments() {
	}

	private static ResourceKey<Enchantment> key(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, SuperAwesomeCraft.id(name));
	}

	/** The level of {@code key} on {@code item}, or 0 if it's missing (or the item is broken). */
	public static int level(HolderLookup.Provider registries, ItemInstance item, ResourceKey<Enchantment> key) {
		return registries.lookupOrThrow(Registries.ENCHANTMENT).get(key)
			.map(holder -> EnchantmentHelper.getItemEnchantmentLevel((Holder<Enchantment>) holder, item))
			.orElse(0);
	}
}
