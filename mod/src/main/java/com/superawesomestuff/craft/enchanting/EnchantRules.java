package com.superawesomestuff.craft.enchanting;

import com.superawesomestuff.craft.SuperAwesomeCraft;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jspecify.annotations.Nullable;

/**
 * The Season 1 enchanting rules (docs/season-1/enchanting/recipes.md). Pure logic over an item, shared by the
 * server menu and the client screen so both always agree.
 *
 * <ul>
 * <li>Every enchantment uses one slot at any level. Curses use none.</li>
 * <li>Slots come from the item's material (item tags {@code superawesomecraft:enchant_slots/1..4}) plus
 * {@link SlotUpgrade}s.</li>
 * <li>Damage enchantments can be mixed on one weapon and Protections on one armour piece; each still takes a slot.
 * Otherwise vanilla exclusivity applies.</li>
 * <li>Each level costs its recipe; levels IV and V also cost Blaze Rods.</li>
 * </ul>
 */
public final class EnchantRules {
	public static final int MAX_LEVEL_WITHOUT_BLAZE_RODS = 3;
	private static final List<TagKey<Item>> SLOT_TAGS = List.of(
		slotTag(4), slotTag(3), slotTag(2), slotTag(1)
	);

	/** Groups vanilla makes exclusive but Season 1 lets you mix, since each enchantment already takes a slot. */
	public enum Group {
		DAMAGE(EnchantmentTags.DAMAGE_EXCLUSIVE),
		PROTECTION(EnchantmentTags.ARMOR_EXCLUSIVE);

		public final TagKey<Enchantment> tag;

		Group(TagKey<Enchantment> tag) {
			this.tag = tag;
		}
	}

	public enum Status {
		OK,
		NO_RECIPE,
		UNSUPPORTED,
		MAX_LEVEL,
		NO_SLOT,
		INCOMPATIBLE,
		ALREADY_APPLIED,
		EMPTY;

		public String translationKey() {
			return "superawesomecraft.enchanting.status." + this.name().toLowerCase(java.util.Locale.ROOT);
		}
	}

	/** Whether an action is allowed, and what it costs if it is. */
	public record Check(Status status, List<Cost> costs) {
		static Check fail(Status status) {
			return new Check(status, List.of());
		}

		public boolean ok() {
			return this.status == Status.OK;
		}
	}

	private EnchantRules() {
	}

	private static TagKey<Item> slotTag(int slots) {
		return TagKey.create(Registries.ITEM, SuperAwesomeCraft.id("enchant_slots/" + slots));
	}

	public static ItemEnchantments enchantments(ItemStack stack) {
		return stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
	}

	public static Optional<Group> group(Holder<Enchantment> enchantment) {
		for (Group group : Group.values()) {
			if (enchantment.is(group.tag)) {
				return Optional.of(group);
			}
		}
		return Optional.empty();
	}

	public static boolean isCurse(Holder<Enchantment> enchantment) {
		return enchantment.is(EnchantmentTags.CURSE);
	}

	public static @Nullable Identifier id(Holder<Enchantment> enchantment) {
		return enchantment.unwrapKey().map(ResourceKey::identifier).orElse(null);
	}

	public static @Nullable Cost perLevel(Holder<Enchantment> enchantment) {
		Identifier id = id(enchantment);
		return id == null ? null : EnchantingRecipes.perLevel(id);
	}

	// Slots

	public static int baseSlots(ItemStack stack) {
		for (int i = 0; i < SLOT_TAGS.size(); i++) {
			if (stack.is(SLOT_TAGS.get(i))) {
				return 4 - i;
			}
		}
		return 1;
	}

	public static int upgradeBits(ItemStack stack) {
		return stack.getOrDefault(ModComponents.SLOT_UPGRADES, 0);
	}

	public static boolean hasUpgrade(ItemStack stack, SlotUpgrade upgrade) {
		return (upgradeBits(stack) & upgrade.bit()) != 0;
	}

	public static int totalSlots(ItemStack stack) {
		return baseSlots(stack) + Integer.bitCount(upgradeBits(stack));
	}

	public static int usedSlots(ItemStack stack) {
		int used = 0;
		for (Holder<Enchantment> enchantment : enchantments(stack).keySet()) {
			if (!isCurse(enchantment)) {
				used++;
			}
		}
		return used;
	}

	/** Vanilla compatibility, except that enchantments in the same shared-cap group can be mixed. */
	public static boolean compatible(Holder<Enchantment> a, Holder<Enchantment> b) {
		if (a.equals(b)) {
			return true;
		}
		Optional<Group> group = group(a);
		if (group.isPresent() && b.is(group.get().tag)) {
			return true;
		}
		return Enchantment.areCompatible(a, b);
	}

	/** Every enchantment the table can put on this item, ordered: ones already on it first, then by name. */
	public static List<Holder<Enchantment>> options(ItemStack stack, HolderLookup.RegistryLookup<Enchantment> registry) {
		ItemEnchantments current = enchantments(stack);
		List<Holder<Enchantment>> result = new ArrayList<>();
		registry.listElements().forEach(holder -> {
			if (current.getLevel(holder) > 0 || (perLevel(holder) != null && holder.value().isSupportedItem(stack))) {
				result.add(holder);
			}
		});
		result.sort(Comparator.<Holder<Enchantment>>comparingInt(h -> current.getLevel(h) > 0 ? 0 : 1)
			.thenComparing(h -> String.valueOf(id(h))));
		return result;
	}

	// Adding a level

	public static Check checkAddLevel(ItemStack stack, Holder<Enchantment> enchantment) {
		if (stack.isEmpty()) {
			return Check.fail(Status.EMPTY);
		}
		Cost perLevel = perLevel(enchantment);
		if (perLevel == null || isCurse(enchantment)) {
			return Check.fail(Status.NO_RECIPE);
		}
		if (!enchantment.value().isSupportedItem(stack)) {
			return Check.fail(Status.UNSUPPORTED);
		}
		ItemEnchantments current = enchantments(stack);
		int level = current.getLevel(enchantment);
		if (level >= enchantment.value().getMaxLevel()) {
			return Check.fail(Status.MAX_LEVEL);
		}
		if (level == 0) {
			if (usedSlots(stack) >= totalSlots(stack)) {
				return Check.fail(Status.NO_SLOT);
			}
			for (Holder<Enchantment> other : current.keySet()) {
				if (!compatible(enchantment, other)) {
					return Check.fail(Status.INCOMPATIBLE);
				}
			}
		}
		List<Cost> costs = new ArrayList<>();
		costs.add(perLevel);
		if (level + 1 > MAX_LEVEL_WITHOUT_BLAZE_RODS) {
			costs.add(EnchantingRecipes.blazeRods());
		}
		return new Check(Status.OK, costs);
	}

	public static void addLevel(ItemStack stack, Holder<Enchantment> enchantment) {
		int level = enchantments(stack).getLevel(enchantment);
		EnchantmentHelper.updateEnchantments(stack, mutable -> mutable.set(enchantment, level + 1));
	}

	// Removing

	public static Check checkRemove(ItemStack stack, Holder<Enchantment> enchantment) {
		if (enchantments(stack).getLevel(enchantment) <= 0) {
			return Check.fail(Status.EMPTY);
		}
		return new Check(Status.OK, isCurse(enchantment) ? List.of(EnchantingRecipes.CURSE_REMOVAL) : List.of());
	}

	public static void remove(ItemStack stack, Holder<Enchantment> enchantment) {
		EnchantmentHelper.updateEnchantments(stack, mutable -> mutable.removeIf(enchantment::equals));
	}

	// Slot upgrades

	public static Check checkUpgrade(ItemStack stack, SlotUpgrade upgrade) {
		if (!SlotEnchantingMenu.canEnchant(stack)) {
			return Check.fail(Status.EMPTY);
		}
		if (hasUpgrade(stack, upgrade)) {
			return Check.fail(Status.ALREADY_APPLIED);
		}
		return new Check(Status.OK, upgrade.costs());
	}

	public static void applyUpgrade(ItemStack stack, SlotUpgrade upgrade) {
		stack.set(ModComponents.SLOT_UPGRADES, upgradeBits(stack) | upgrade.bit());
	}
}
