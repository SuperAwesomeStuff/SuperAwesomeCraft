package com.superawesomestuff.craft.enchanting;

import com.superawesomestuff.craft.SuperAwesomeCraft;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.List;
import java.util.ListIterator;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import org.jspecify.annotations.Nullable;

/**
 * Random sources never skip the climb (docs/season-1/enchanting/README.md): loot, fishing, mob gear and trades cap
 * enchantments at level III, Mending never appears, Librarians sell Convenience books only, and Villagers sell
 * unenchanted gear.
 */
public final class RandomEnchantLimits {
	public static final TagKey<Enchantment> CONVENIENCE = TagKey.create(Registries.ENCHANTMENT, SuperAwesomeCraft.id("convenience"));

	private RandomEnchantLimits() {
	}

	public static void init() {
		LootTableEvents.MODIFY_DROPS.register((table, context, drops) -> limitAll(drops));
	}

	public static void limitAll(List<ItemStack> drops) {
		for (ListIterator<ItemStack> it = drops.listIterator(); it.hasNext(); ) {
			ItemStack stack = it.next();
			limit(stack);
			if (stack.is(Items.ENCHANTED_BOOK) && stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY).isEmpty()) {
				it.set(stack.transmuteCopy(Items.BOOK));
			}
		}
	}

	/** Caps every enchantment at III and removes Mending. Curses are left alone. */
	public static void limit(ItemStack stack) {
		limit(stack, DataComponents.ENCHANTMENTS);
		limit(stack, DataComponents.STORED_ENCHANTMENTS);
	}

	private static void limit(ItemStack stack, DataComponentType<ItemEnchantments> type) {
		ItemEnchantments enchantments = stack.get(type);
		if (enchantments == null || enchantments.isEmpty()) {
			return;
		}
		ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(enchantments);
		for (Object2IntMap.Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
			Holder<Enchantment> enchantment = entry.getKey();
			if (enchantment.is(Enchantments.MENDING)) {
				mutable.removeIf(enchantment::equals);
			} else if (!EnchantRules.isCurse(enchantment) && entry.getIntValue() > EnchantRules.RANDOM_LEVEL_CAP) {
				mutable.set(enchantment, EnchantRules.RANDOM_LEVEL_CAP);
			}
		}
		stack.set(type, mutable.toImmutable());
	}

	/** Returns the offer to keep (possibly changed), or null to make the Villager pick a different trade. */
	public static @Nullable MerchantOffer limitOffer(@Nullable MerchantOffer offer) {
		if (offer == null) {
			return null;
		}
		ItemStack result = offer.getResult();
		ItemEnchantments stored = result.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);
		for (Holder<Enchantment> enchantment : stored.keySet()) {
			if (!enchantment.is(CONVENIENCE)) {
				return null;
			}
		}
		limit(result);
		if (result.is(Items.ENCHANTED_BOOK) && result.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY).isEmpty()) {
			return null;
		}
		if (result.has(DataComponents.ENCHANTMENTS)) {
			result.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
		}
		return offer;
	}
}
