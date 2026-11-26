package com.superawesomestuff.craft.playtest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.superawesomestuff.craft.enchanting.EnchantRules;
import com.superawesomestuff.craft.enchanting.SlotUpgrade;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/** Turns players and items into the JSON the playtest log writes. */
final class Snapshots {
	private Snapshots() {
	}

	/** Dimension and block position. */
	static JsonObject where(Player player) {
		JsonObject data = new JsonObject();
		data.addProperty("dim", player.level().dimension().identifier().toString());
		data.addProperty("x", player.getBlockX());
		data.addProperty("y", player.getBlockY());
		data.addProperty("z", player.getBlockZ());
		return data;
	}

	/**
	 * Where the player is, their health and food, and every piece of gear they carry: anything enchantable or damageable,
	 * plus enchanted books. Slot numbers are the inventory's (0-8 hotbar, 9-35 main, 36+ armour and offhand).
	 */
	static JsonObject snapshot(ServerPlayer player) {
		JsonObject data = where(player);
		data.addProperty("health", Math.round(player.getHealth()));
		data.addProperty("max_health", Math.round(player.getMaxHealth()));
		data.addProperty("food", player.getFoodData().getFoodLevel());
		data.addProperty("mode", player.gameMode().getName());
		JsonArray gear = new JsonArray();
		Inventory inventory = player.getInventory();
		for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
			ItemStack stack = inventory.getItem(slot);
			if (isGear(stack)) {
				JsonObject item = item(stack);
				item.addProperty("slot", slot);
				gear.add(item);
			}
		}
		data.add("gear", gear);
		return data;
	}

	private static boolean isGear(ItemStack stack) {
		return !stack.isEmpty() && (stack.isDamageableItem() || stack.isEnchanted() || stack.has(DataComponents.STORED_ENCHANTMENTS));
	}

	static JsonObject item(ItemStack stack) {
		JsonObject item = new JsonObject();
		item.addProperty("id", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
		if (stack.getCount() > 1) {
			item.addProperty("count", stack.getCount());
		}
		JsonObject enchantments = enchantments(stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
		if (!enchantments.isEmpty()) {
			item.add("ench", enchantments);
		}
		JsonObject stored = enchantments(stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY));
		if (!stored.isEmpty()) {
			item.add("stored", stored);
		}
		if (stack.isDamageableItem()) {
			item.addProperty("damage", stack.getDamageValue());
			item.addProperty("max_damage", stack.getMaxDamage());
			item.addProperty("slots_used", EnchantRules.usedSlots(stack));
			item.addProperty("slots", EnchantRules.totalSlots(stack));
			if (isMaxed(stack)) {
				item.addProperty("maxed", true);
			}
			JsonArray upgrades = new JsonArray();
			for (SlotUpgrade upgrade : SlotUpgrade.values()) {
				if (EnchantRules.hasUpgrade(stack, upgrade)) {
					upgrades.add(upgrade.key());
				}
			}
			if (!upgrades.isEmpty()) {
				item.add("upgrades", upgrades);
			}
		}
		return item;
	}

	/** Every slot used and every enchantment on it at its top level: as far as enchanting can take this item. */
	static boolean isMaxed(ItemStack stack) {
		ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
		if (EnchantRules.usedSlots(stack) < EnchantRules.totalSlots(stack)) {
			return false;
		}
		for (var entry : enchantments.entrySet()) {
			if (!EnchantRules.isCurse(entry.getKey()) && entry.getIntValue() < entry.getKey().value().getMaxLevel()) {
				return false;
			}
		}
		return true;
	}

	private static JsonObject enchantments(ItemEnchantments enchantments) {
		TreeMap<String, Integer> sorted = new TreeMap<>();
		for (var entry : enchantments.entrySet()) {
			Holder<Enchantment> holder = entry.getKey();
			Identifier id = EnchantRules.id(holder);
			sorted.put(id == null ? "unknown" : id.toString(), entry.getIntValue());
		}
		JsonObject json = new JsonObject();
		sorted.forEach(json::addProperty);
		return json;
	}

	static JsonObject counts(Map<String, Integer> counts) {
		JsonObject json = new JsonObject();
		new TreeMap<>(counts).forEach(json::addProperty);
		return json;
	}
}
