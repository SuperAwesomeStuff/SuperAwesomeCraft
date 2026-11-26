package com.superawesomestuff.craft.gametest;

import com.google.gson.JsonObject;
import com.superawesomestuff.craft.enchanting.SlotEnchantingMenu;
import com.superawesomestuff.craft.playtest.PlaytestLog;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;

/** Run with {@code ./gradlew runGametest}. Covers the playtest log (docs/season-1/enchanting/README.md#playtest-measures). */
public class PlaytestLogTests {
	/** Every event written for {@code player} while {@code body} runs. */
	private static List<JsonObject> recording(Player player, Runnable body) {
		List<JsonObject> events = new CopyOnWriteArrayList<>();
		String id = player.getUUID().toString();
		Consumer<JsonObject> listener = line -> {
			if (id.equals(line.get("player").getAsString())) {
				events.add(line);
			}
		};
		PlaytestLog.addListener(listener);
		try {
			body.run();
		} finally {
			PlaytestLog.removeListener(listener);
		}
		return events;
	}

	@GameTest
	public void enchantingRecordsWhatWasAddedAndWhatItCost(GameTestHelper helper) {
		Player player = helper.makeMockPlayer(GameType.SURVIVAL);
		SlotEnchantingMenu menu = new SlotEnchantingMenu(1, player.getInventory(), ContainerLevelAccess.NULL);
		menu.getSlot(SlotEnchantingMenu.ITEM_SLOT).set(new ItemStack(Items.IRON_PICKAXE));
		menu.getSlot(SlotEnchantingMenu.FIRST_MATERIAL_SLOT).set(new ItemStack(Items.REDSTONE, 64));
		var registry = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
		int efficiency = registry.getId(registry.getOrThrow(Enchantments.EFFICIENCY).value());

		List<JsonObject> events = recording(player, () -> {
			helper.assertTrue(menu.clickMenuButton(player, SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_ADD, efficiency)), "add Efficiency");
			helper.assertTrue(menu.clickMenuButton(player, SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_REMOVE, efficiency)), "remove it");
		});

		helper.assertValueEqual(events.size(), 2, "events");
		JsonObject added = events.get(0);
		helper.assertValueEqual(added.get("type").getAsString(), "enchant", "first event");
		helper.assertValueEqual(added.get("enchantment").getAsString(), "minecraft:efficiency", "enchantment");
		helper.assertValueEqual(added.get("to").getAsInt(), 1, "new level");
		helper.assertValueEqual(added.get("via").getAsString(), "materials", "paid with");
		helper.assertTrue(added.getAsJsonArray("cost").size() > 0, "the cost is recorded");
		helper.assertValueEqual(added.getAsJsonObject("item").get("id").getAsString(), "minecraft:iron_pickaxe", "item");
		helper.assertValueEqual(events.get(1).get("type").getAsString(), "unenchant", "second event");
		helper.succeed();
	}

	@GameTest
	public void gearHittingZeroIsRecordedOnce(GameTestHelper helper) {
		ServerPlayer player = (ServerPlayer) helper.makeMockServerPlayer(GameType.SURVIVAL);
		ItemStack pick = new ItemStack(Items.IRON_PICKAXE);
		List<JsonObject> events = recording(player, () -> {
			pick.hurtAndBreak(10_000, helper.getLevel(), player, broken -> helper.fail("The pickaxe broke"));
			pick.hurtAndBreak(1, helper.getLevel(), player, broken -> helper.fail("The pickaxe broke"));
		});
		helper.assertValueEqual(events.size(), 1, "events");
		helper.assertValueEqual(events.get(0).get("type").getAsString(), "gear_broken", "event");
		helper.succeed();
	}

	@GameTest
	public void itemsRecordEnchantmentsSlotsAndDamage(GameTestHelper helper) {
		ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
		sword.enchant(helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SHARPNESS), 3);
		sword.setDamageValue(10);
		JsonObject item = PlaytestLog.item(sword);
		helper.assertValueEqual(item.getAsJsonObject("ench").get("minecraft:sharpness").getAsInt(), 3, "sharpness");
		helper.assertValueEqual(item.get("damage").getAsInt(), 10, "damage");
		helper.assertValueEqual(item.get("slots").getAsInt(), 3, "diamond slots");
		helper.assertValueEqual(item.get("slots_used").getAsInt(), 1, "slots used");
		helper.assertFalse(item.has("maxed"), "a sword with free slots isn't maxed");

		ItemStack pick = new ItemStack(Items.WOODEN_PICKAXE);
		pick.enchant(helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), 1);
		helper.assertTrue(PlaytestLog.item(pick).has("maxed"), "a one-slot pick with Silk Touch is maxed");
		helper.succeed();
	}
}
