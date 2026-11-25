package com.superawesomestuff.craft.gametest;

import com.superawesomestuff.craft.enchanting.EnchantingRecipes;
import com.superawesomestuff.craft.enchanting.Cost;
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
import net.minecraft.resources.Identifier;
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
	public void damageEnchantmentsAndProtectionsMixFreely(GameTestHelper helper) {
		ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
		add(helper, sword, Enchantments.SHARPNESS, 5);
		add(helper, sword, Enchantments.SMITE, 5);
		add(helper, sword, Enchantments.BANE_OF_ARTHROPODS, 1);
		helper.assertValueEqual(EnchantRules.usedSlots(sword), 3, "each damage enchantment takes a slot");

		ItemStack chest = new ItemStack(Items.NETHERITE_CHESTPLATE);
		add(helper, chest, Enchantments.PROTECTION, 4);
		add(helper, chest, Enchantments.BLAST_PROTECTION, 4);
		add(helper, chest, Enchantments.FIRE_PROTECTION, 1);
		helper.succeed();
	}

	@GameTest
	public void fourthAndFifthLevelsNeedBlazeRods(GameTestHelper helper) {
		ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
		add(helper, sword, Enchantments.SHARPNESS, 3);
		EnchantRules.Check fourth = EnchantRules.checkAddLevel(sword, ench(helper, Enchantments.SHARPNESS));
		helper.assertTrue(fourth.costs().stream().anyMatch(c -> c.display() == Items.BLAZE_ROD), "Level IV should need Blaze Rods");
		EnchantRules.Check smite = EnchantRules.checkAddLevel(sword, ench(helper, Enchantments.SMITE));
		helper.assertFalse(smite.costs().stream().anyMatch(c -> c.display() == Items.BLAZE_ROD), "Smite I needs no Blaze Rods");

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

	private static ResourceKey<Enchantment> sac(String name) {
		return ResourceKey.create(Registries.ENCHANTMENT, com.superawesomestuff.craft.SuperAwesomeCraft.id(name));
	}

	@GameTest
	public void booksAreVouchersForOneLevelEach(GameTestHelper helper) {
		Player player = helper.makeMockPlayer(GameType.SURVIVAL);
		SlotEnchantingMenu menu = new SlotEnchantingMenu(1, player.getInventory(), ContainerLevelAccess.NULL);
		menu.getSlot(SlotEnchantingMenu.ITEM_SLOT).set(new ItemStack(Items.IRON_SWORD));
		ItemStack book = net.minecraft.world.item.enchantment.EnchantmentHelper.createBook(
			new net.minecraft.world.item.enchantment.EnchantmentInstance(ench(helper, Enchantments.KNOCKBACK), 2));
		menu.getSlot(SlotEnchantingMenu.FIRST_MATERIAL_SLOT).set(book);
		int knockback = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
			.getId(ench(helper, Enchantments.KNOCKBACK).value());
		int button = SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_ADD, knockback);

		helper.assertTrue(menu.clickMenuButton(player, button), "the book pays for level I");
		helper.assertTrue(menu.clickMenuButton(player, button), "and for level II");
		helper.assertValueEqual(EnchantRules.enchantments(menu.item()).getLevel(ench(helper, Enchantments.KNOCKBACK)), 2, "knockback level");
		helper.assertTrue(menu.getSlot(SlotEnchantingMenu.FIRST_MATERIAL_SLOT).getItem().is(Items.BOOK), "the used-up book is a plain book");
		helper.assertFalse(menu.clickMenuButton(player, button), "nothing left to pay with");
		helper.succeed();
	}

	@GameTest
	public void newEnchantmentsExistAndFollowTheRules(GameTestHelper helper) {
		for (String name : new String[]{"bane_of_illagers", "frost_aspect", "reach", "step_assist", "rebounding", "bulwark",
			"beheading", "smelting", "excavating", "felling"}) {
			ench(helper, sac(name));
		}
		ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
		add(helper, sword, Enchantments.SHARPNESS, 4);
		add(helper, sword, sac("bane_of_illagers"), 1);
		add(helper, sword, sac("frost_aspect"), 1);
		expect(helper, sword, Enchantments.FIRE_ASPECT, Status.INCOMPATIBLE);

		ItemStack axe = new ItemStack(Items.IRON_AXE);
		add(helper, axe, Enchantments.BREACH, 1);

		ItemStack pick = new ItemStack(Items.DIAMOND_PICKAXE);
		add(helper, pick, sac("smelting"), 1);
		expect(helper, pick, Enchantments.SILK_TOUCH, Status.INCOMPATIBLE);
		helper.succeed();
	}

	@GameTest
	public void fellingCutsTheWholeTreeUnlessCrouching(GameTestHelper helper) {
		for (int y = 1; y <= 4; y++) {
			helper.setBlock(1, y, 1, net.minecraft.world.level.block.Blocks.OAK_LOG);
			helper.setBlock(3, y, 1, net.minecraft.world.level.block.Blocks.OAK_LOG);
		}
		net.minecraft.server.level.ServerPlayer player = helper.makeMockServerPlayerInLevel();
		player.setGameMode(GameType.SURVIVAL);
		ItemStack axe = new ItemStack(Items.IRON_AXE);
		axe.enchant(ench(helper, sac("felling")), 1);
		player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, axe);

		player.gameMode.destroyBlock(helper.absolutePos(new net.minecraft.core.BlockPos(1, 1, 1)));
		helper.assertBlockPresent(net.minecraft.world.level.block.Blocks.AIR, 1, 4, 1);

		player.setShiftKeyDown(true);
		player.gameMode.destroyBlock(helper.absolutePos(new net.minecraft.core.BlockPos(3, 1, 1)));
		helper.assertBlockPresent(net.minecraft.world.level.block.Blocks.OAK_LOG, 3, 4, 1);
		helper.succeed();
	}

	@GameTest(maxTicks = 100)
	public void beheadingDropsVanillaHeads(GameTestHelper helper) {
		net.minecraft.server.level.ServerPlayer player = helper.makeMockServerPlayerInLevel();
		player.setGameMode(GameType.SURVIVAL);
		ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
		sword.enchant(ench(helper, sac("beheading")), 3);
		player.setItemInHand(net.minecraft.world.InteractionHand.MAIN_HAND, sword);
		for (int i = 0; i < 40; i++) {
			var zombie = helper.spawn(net.minecraft.world.entity.EntityTypes.ZOMBIE, new net.minecraft.core.BlockPos(2, 2, 2));
			zombie.hurtServer(helper.getLevel(), helper.getLevel().damageSources().playerAttack(player), 1000);
		}
		helper.runAfterDelay(5, () -> {
			helper.assertItemEntityPresent(Items.ZOMBIE_HEAD);
			helper.succeed();
		});
	}

	@GameTest
	public void randomSourcesCapAtThreeAndDropMending(GameTestHelper helper) {
		ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
		sword.enchant(ench(helper, Enchantments.SHARPNESS), 5);
		sword.enchant(ench(helper, Enchantments.MENDING), 1);
		sword.enchant(ench(helper, Enchantments.VANISHING_CURSE), 1);
		com.superawesomestuff.craft.enchanting.RandomEnchantLimits.limit(sword);
		helper.assertValueEqual(EnchantRules.enchantments(sword).getLevel(ench(helper, Enchantments.SHARPNESS)), 3, "sharpness");
		helper.assertValueEqual(EnchantRules.enchantments(sword).getLevel(ench(helper, Enchantments.MENDING)), 0, "mending");
		helper.assertValueEqual(EnchantRules.enchantments(sword).getLevel(ench(helper, Enchantments.VANISHING_CURSE)), 1, "curse");
		helper.succeed();
	}

	@GameTest
	public void villagersSellOnlyConvenienceBooksAndPlainGear(GameTestHelper helper) {
		ItemStack powerBook = net.minecraft.world.item.enchantment.EnchantmentHelper.createBook(
			new net.minecraft.world.item.enchantment.EnchantmentInstance(ench(helper, Enchantments.SHARPNESS), 2));
		helper.assertTrue(com.superawesomestuff.craft.enchanting.RandomEnchantLimits.limitOffer(offer(powerBook)) == null, "sharpness book refused");

		ItemStack comfortBook = net.minecraft.world.item.enchantment.EnchantmentHelper.createBook(
			new net.minecraft.world.item.enchantment.EnchantmentInstance(ench(helper, Enchantments.EFFICIENCY), 5));
		var kept = com.superawesomestuff.craft.enchanting.RandomEnchantLimits.limitOffer(offer(comfortBook));
		helper.assertTrue(kept != null, "efficiency book kept");
		helper.assertValueEqual(kept.getResult().getOrDefault(net.minecraft.core.component.DataComponents.STORED_ENCHANTMENTS,
			net.minecraft.world.item.enchantment.ItemEnchantments.EMPTY).getLevel(ench(helper, Enchantments.EFFICIENCY)), 3, "efficiency capped");

		ItemStack pick = new ItemStack(Items.DIAMOND_PICKAXE);
		pick.enchant(ench(helper, Enchantments.EFFICIENCY), 2);
		var plain = com.superawesomestuff.craft.enchanting.RandomEnchantLimits.limitOffer(offer(pick));
		helper.assertTrue(EnchantRules.enchantments(plain.getResult()).isEmpty(), "gear sold unenchanted");
		helper.succeed();
	}

	private static net.minecraft.world.item.trading.MerchantOffer offer(ItemStack result) {
		return new net.minecraft.world.item.trading.MerchantOffer(new net.minecraft.world.item.trading.ItemCost(Items.EMERALD, 1), result, 1, 1, 0.05F);
	}

	@GameTest
	public void tablePaysFromMaterialSlotsAndRefusesWhenShort(GameTestHelper helper) {
		Player player = helper.makeMockPlayer(GameType.SURVIVAL);
		SlotEnchantingMenu menu = new SlotEnchantingMenu(1, player.getInventory(), ContainerLevelAccess.NULL);
		menu.getSlot(SlotEnchantingMenu.ITEM_SLOT).set(new ItemStack(Items.IRON_PICKAXE));
		int efficiency = helper.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
			.getId(ench(helper, Enchantments.EFFICIENCY).value());
		int button = SlotEnchantingMenu.button(SlotEnchantingMenu.ACTION_ADD, efficiency);

		player.getInventory().add(new ItemStack(Items.REDSTONE, 64));
		helper.assertFalse(menu.clickMenuButton(player, button), "redstone in the inventory doesn't count");

		menu.getSlot(SlotEnchantingMenu.FIRST_MATERIAL_SLOT).set(new ItemStack(Items.REDSTONE, 20));
		helper.assertFalse(menu.clickMenuButton(player, button), "20 redstone is not enough");

		menu.getSlot(SlotEnchantingMenu.LAST_MATERIAL_SLOT).set(new ItemStack(Items.REDSTONE, 20));
		helper.assertTrue(menu.clickMenuButton(player, button), "40 redstone across both slots is enough");
		helper.assertValueEqual(EnchantRules.enchantments(menu.item()).getLevel(ench(helper, Enchantments.EFFICIENCY)), 1, "efficiency level");
		helper.assertValueEqual(menu.materialCount(com.superawesomestuff.craft.enchanting.Cost.of(Items.REDSTONE, 1)), 8, "redstone left in the slots");
		helper.assertValueEqual(player.getInventory().countItem(Items.REDSTONE), 64, "inventory untouched");
		helper.succeed();
	}

	@GameTest
	public void booksCannotBeEnchanted(GameTestHelper helper) {
		helper.assertFalse(SlotEnchantingMenu.canEnchant(new ItemStack(Items.BOOK)), "book");
		helper.assertFalse(SlotEnchantingMenu.canEnchant(new ItemStack(Items.ENCHANTED_BOOK)), "enchanted book");
		helper.assertTrue(SlotEnchantingMenu.canEnchant(new ItemStack(Items.SHIELD)), "shield");
		helper.assertTrue(SlotEnchantingMenu.canEnchant(new ItemStack(Items.IRON_SWORD)), "sword");
		helper.assertFalse(EnchantRules.checkUpgrade(new ItemStack(Items.BOOK), SlotUpgrade.OVERWORLD).ok(), "no slot upgrades on books");
		helper.succeed();
	}

	@GameTest
	public void recipeConfigRoundTrips(GameTestHelper helper) {
		EnchantingRecipes.Table defaults = EnchantingRecipes.defaults();
		EnchantingRecipes.Table parsed = EnchantingRecipes.fromJson(EnchantingRecipes.toJson(defaults));
		helper.assertValueEqual(parsed, defaults, "defaults survive writing and reading the config");
		helper.succeed();
	}

	@GameTest
	public void recipeConfigChangesCosts(GameTestHelper helper) {
		EnchantingRecipes.Table table = EnchantingRecipes.fromJson("""
			{
			  "high_levels": { "from_level": 3, "cost": { "item": "minecraft:emerald", "count": 2 } },
			  "enchantments": {
			    "minecraft:sharpness": { "item": "minecraft:quartz", "count": 10 },
			    "minecraft:silk_touch": { "tag": "#minecraft:wool", "item": "minecraft:white_wool", "count": 5 },
			    "minecraft:smite": { "item": "minecraft:not_an_item", "count": 10 },
			    "minecraft:knockback": { "item": "minecraft:slime_ball" }
			  }
			}
			""");
		helper.assertValueEqual(table.perLevel().get(Identifier.withDefaultNamespace("sharpness")), Cost.of(Items.QUARTZ, 10), "sharpness");
		helper.assertTrue(table.perLevel().get(Identifier.withDefaultNamespace("silk_touch")).matches(new ItemStack(Items.WOOL.pick(net.minecraft.world.item.DyeColor.RED))), "any wool");
		helper.assertFalse(table.perLevel().containsKey(Identifier.withDefaultNamespace("smite")), "unknown items are skipped");
		helper.assertFalse(table.perLevel().containsKey(Identifier.withDefaultNamespace("knockback")), "entries without a count are skipped");
		helper.assertFalse(table.perLevel().containsKey(Identifier.withDefaultNamespace("power")), "left-out enchantments can't be made");
		helper.assertValueEqual(table.highLevelFrom(), 3, "high levels start at III");
		helper.assertValueEqual(table.highLevelCost(), Cost.of(Items.EMERALD, 2), "high level cost");
		helper.assertValueEqual(table.curseRemoval(), EnchantingRecipes.defaults().curseRemoval(), "missing sections keep the defaults");
		helper.succeed();
	}
}
