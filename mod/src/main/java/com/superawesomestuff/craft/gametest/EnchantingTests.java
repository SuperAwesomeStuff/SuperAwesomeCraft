package com.superawesomestuff.craft.gametest;

import com.superawesomestuff.craft.enchanting.EnchantRules;
import com.superawesomestuff.craft.enchanting.EnchantRules.Status;
import com.superawesomestuff.craft.enchanting.SlotEnchantingMenu;
import com.superawesomestuff.craft.enchanting.SlotUpgrade;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;

/** Run with {@code ./gradlew runGametest}. Covers docs/season-1/enchanting/recipes.md. */
public class EnchantingTests {
	private static Holder<Enchantment> ench(GameTestHelper helper, ResourceKey<Enchantment> key) {
		return helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key);
	}

	private static void add(GameTestHelper helper, ItemStack stack, ResourceKey<Enchantment> key, int levels) {
		Holder<Enchantment> holder = ench(helper, key);
		for (int i = 0; i < levels; i++) {
			EnchantRules.Check check = EnchantRules.checkAddLevel(stack, holder);
			helper.assertTrue(check.ok(), "Expected to add " + key.identifier() + " but got " + check.status());
			EnchantRules.addLevel(stack, holder);
		}
	}

	private static void expect(GameTestHelper helper, ItemStack stack, ResourceKey<Enchantment> key, Status status) {
		helper.assertValueEqual(EnchantRules.checkAddLevel(stack, ench(helper, key)).status(), status, key.identifier().toString());
	}

	@GameTest
	public void slotsComeFromMaterial(GameTestHelper helper) {
		helper.assertValueEqual(EnchantRules.totalSlots(new ItemStack(Items.WOODEN_PICKAXE)), 1, "wood");
		helper.assertValueEqual(EnchantRules.totalSlots(new ItemStack(Items.IRON_SWORD)), 2, "iron");
		helper.assertValueEqual(EnchantRules.totalSlots(new ItemStack(Items.BOW)), 2, "bow");
		helper.assertValueEqual(EnchantRules.totalSlots(new ItemStack(Items.DIAMOND_CHESTPLATE)), 3, "diamond");
		helper.assertValueEqual(EnchantRules.totalSlots(new ItemStack(Items.NETHERITE_AXE)), 4, "netherite");
		helper.succeed();
	}

	@GameTest
	public void oneSlotPerEnchantmentAtAnyLevel(GameTestHelper helper) {
		ItemStack pick = new ItemStack(Items.DIAMOND_PICKAXE);
		add(helper, pick, Enchantments.EFFICIENCY, 3);
		add(helper, pick, Enchantments.UNBREAKING, 3);
		add(helper, pick, Enchantments.SILK_TOUCH, 1);
		helper.assertValueEqual(EnchantRules.usedSlots(pick), 3, "used slots");
		expect(helper, pick, Enchantments.EFFICIENCY, Status.OK);
		expect(helper, pick, Enchantments.SILK_TOUCH, Status.MAX_LEVEL);
		helper.succeed();
	}

	@GameTest
	public void fullItemHasNoSlotForANewEnchantment(GameTestHelper helper) {
		ItemStack sword = new ItemStack(Items.IRON_SWORD);
		add(helper, sword, Enchantments.SHARPNESS, 1);
		add(helper, sword, Enchantments.LOOTING, 1);
		expect(helper, sword, Enchantments.KNOCKBACK, Status.NO_SLOT);
		helper.succeed();
	}

	@GameTest
	public void silkTouchAndFortuneStillConflict(GameTestHelper helper) {
		ItemStack pick = new ItemStack(Items.DIAMOND_PICKAXE);
		add(helper, pick, Enchantments.SILK_TOUCH, 1);
		expect(helper, pick, Enchantments.FORTUNE, Status.INCOMPATIBLE);
		helper.succeed();
	}

	@GameTest
	public void damageEnchantmentsMixUnderASharedCapOfFive(GameTestHelper helper) {
		ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
		add(helper, sword, Enchantments.SHARPNESS, 3);
		add(helper, sword, Enchantments.SMITE, 1);
		add(helper, sword, Enchantments.BANE_OF_ARTHROPODS, 1);
		helper.assertValueEqual(EnchantRules.groupTotal(sword, EnchantRules.Group.DAMAGE), 5, "damage total");
		expect(helper, sword, Enchantments.SHARPNESS, Status.GROUP_FULL);
		helper.succeed();
	}

	@GameTest
	public void protectionsMixUnderASharedCapOfFour(GameTestHelper helper) {
		ItemStack chest = new ItemStack(Items.NETHERITE_CHESTPLATE);
		add(helper, chest, Enchantments.PROTECTION, 2);
		add(helper, chest, Enchantments.BLAST_PROTECTION, 2);
		expect(helper, chest, Enchantments.FIRE_PROTECTION, Status.GROUP_FULL);
		helper.succeed();
	}

	@GameTest
	public void fourthAndFifthLevelsNeedBlazeRodsCountedAcrossTheCap(GameTestHelper helper) {
		ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
		add(helper, sword, Enchantments.SHARPNESS, 3);
		EnchantRules.Check smite = EnchantRules.checkAddLevel(sword, ench(helper, Enchantments.SMITE));
		helper.assertTrue(smite.costs().stream().anyMatch(c -> c.display() == Items.BLAZE_ROD), "4th damage level should need Blaze Rods");

		ItemStack pick = new ItemStack(Items.DIAMOND_PICKAXE);
		add(helper, pick, Enchantments.EFFICIENCY, 2);
		EnchantRules.Check third = EnchantRules.checkAddLevel(pick, ench(helper, Enchantments.EFFICIENCY));
		helper.assertFalse(third.costs().stream().anyMatch(c -> c.display() == Items.BLAZE_ROD), "Level III needs no Blaze Rods");
		helper.succeed();
	}

	@GameTest
	public void cursesUseNoSlotAndMendingCannotBeMade(GameTestHelper helper) {
		ItemStack sword = new ItemStack(Items.IRON_SWORD);
		sword.enchant(ench(helper, Enchantments.VANISHING_CURSE), 1);
		helper.assertValueEqual(EnchantRules.usedSlots(sword), 0, "curse slots");
		expect(helper, sword, Enchantments.MENDING, Status.NO_RECIPE);
		helper.succeed();
	}

	@GameTest
	public void slotUpgradeAddsOneSlotOnce(GameTestHelper helper) {
		ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
		helper.assertTrue(EnchantRules.checkUpgrade(sword, SlotUpgrade.NETHER).ok(), "first upgrade");
		EnchantRules.applyUpgrade(sword, SlotUpgrade.NETHER);
		helper.assertValueEqual(EnchantRules.totalSlots(sword), 4, "slots after upgrade");
		helper.assertValueEqual(EnchantRules.checkUpgrade(sword, SlotUpgrade.NETHER).status(), Status.ALREADY_APPLIED, "second upgrade");
		helper.succeed();
	}

	@GameTest
	public void foundBookAppliesFreeButNeedsASlot(GameTestHelper helper) {
		ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
		book.set(net.minecraft.core.component.DataComponents.STORED_ENCHANTMENTS,
			net.minecraft.world.item.enchantment.ItemEnchantments.EMPTY);
		net.minecraft.world.item.enchantment.EnchantmentHelper.updateEnchantments(book,
			m -> m.set(ench(helper, Enchantments.KNOCKBACK), 2));
		helper.assertValueEqual(book.getOrDefault(net.minecraft.core.component.DataComponents.STORED_ENCHANTMENTS,
			net.minecraft.world.item.enchantment.ItemEnchantments.EMPTY).size(), 1, "book enchantments");

		ItemStack full = new ItemStack(Items.IRON_SWORD);
		add(helper, full, Enchantments.SHARPNESS, 1);
		add(helper, full, Enchantments.LOOTING, 1);
		helper.assertValueEqual(EnchantRules.checkBook(full, book).status(), Status.NO_SLOT, "full sword");

		ItemStack sword = new ItemStack(Items.IRON_SWORD);
		helper.assertTrue(EnchantRules.checkBook(sword, book).ok(), "empty sword takes the book");
		EnchantRules.applyBook(sword, book);
		helper.assertValueEqual(EnchantRules.enchantments(sword).getLevel(ench(helper, Enchantments.KNOCKBACK)), 2, "knockback level");
		helper.succeed();
	}

	@GameTest
	public void tablePaysFromInventoryAndRefusesWhenShort(GameTestHelper helper) {
		Player player = helper.makeMockPlayer(GameType.SURVIVAL);
		SlotEnchantingMenu menu = new SlotEnchantingMenu(1, player.getInventory(), ContainerLevelAccess.NULL);
		menu.getSlot(SlotEnchantingMenu.ITEM_SLOT).set(new ItemStack(Items.IRON_PICKAXE));
		int efficiency = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
			.getId(ench(helper, Enchantments.EFFICIENCY).value());
		int button = SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_ADD, efficiency);

		player.getInventory().add(new ItemStack(Items.REDSTONE, 20));
		helper.assertFalse(menu.clickMenuButton(player, button), "20 redstone is not enough");

		player.getInventory().add(new ItemStack(Items.REDSTONE, 20));
		helper.assertTrue(menu.clickMenuButton(player, button), "40 redstone is enough");
		helper.assertValueEqual(EnchantRules.enchantments(menu.item()).getLevel(ench(helper, Enchantments.EFFICIENCY)), 1, "efficiency level");
		helper.assertValueEqual(player.getInventory().countItem(Items.REDSTONE), 8, "redstone left");
		helper.succeed();
	}
}
