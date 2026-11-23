package com.superawesomestuff.craft.gametest;

import com.superawesomestuff.craft.durability.MaterialRepairRecipe;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.block.Blocks;

/** Run with {@code ./gradlew runGametest}. Covers docs/season-1/enchanting/README.md#durability. */
public class DurabilityTests {
	private static ItemStack brokenPick(GameTestHelper helper) {
		ItemStack pick = new ItemStack(Items.IRON_PICKAXE);
		pick.hurtAndBreak(10_000, helper.getLevel(), null, broken -> helper.fail("The pickaxe broke"));
		return pick;
	}

	@GameTest
	public void gearStopsAtZeroInsteadOfBreaking(GameTestHelper helper) {
		ItemStack pick = brokenPick(helper);
		helper.assertFalse(pick.isEmpty(), "pickaxe should still exist");
		helper.assertTrue(pick.isBroken(), "pickaxe should be broken");
		helper.succeed();
	}

	@GameTest
	public void brokenGearStopsWorking(GameTestHelper helper) {
		ItemStack pick = brokenPick(helper);
		helper.assertValueEqual(pick.getDestroySpeed(Blocks.STONE.defaultBlockState()), 1.0F, "mining speed");
		helper.assertFalse(pick.isCorrectToolForDrops(Blocks.IRON_ORE.defaultBlockState()), "harvests ore");
		AtomicInteger modifiers = new AtomicInteger();
		pick.forEachModifier(EquipmentSlot.MAINHAND, (attribute, modifier) -> modifiers.incrementAndGet());
		helper.assertValueEqual(modifiers.get(), 0, "attribute modifiers");

		ItemStack fresh = new ItemStack(Items.IRON_PICKAXE);
		helper.assertTrue(fresh.isCorrectToolForDrops(Blocks.IRON_ORE.defaultBlockState()), "a working pickaxe harvests ore");
		helper.succeed();
	}

	@GameTest
	public void eachMaterialRepairsHalf(GameTestHelper helper) {
		ItemStack pick = brokenPick(helper);
		CraftingInput one = CraftingInput.of(2, 1, List.of(pick, new ItemStack(Items.IRON_INGOT)));
		helper.assertTrue(MaterialRepairRecipe.INSTANCE.matches(one, helper.getLevel()), "pick + 1 ingot");
		ItemStack half = MaterialRepairRecipe.INSTANCE.assemble(one);
		helper.assertValueEqual(half.getDamageValue(), pick.getMaxDamage() - MaterialRepairRecipe.perMaterial(pick), "damage after one ingot");

		CraftingInput two = CraftingInput.of(3, 1, List.of(pick, new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT)));
		helper.assertValueEqual(MaterialRepairRecipe.INSTANCE.assemble(two).getDamageValue(), 0, "damage after two ingots");

		CraftingInput three = CraftingInput.of(3, 2, List.of(pick, new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT),
			new ItemStack(Items.IRON_INGOT), ItemStack.EMPTY, ItemStack.EMPTY));
		helper.assertFalse(MaterialRepairRecipe.INSTANCE.matches(three, helper.getLevel()), "won't waste a third ingot");

		CraftingInput wrong = CraftingInput.of(2, 1, List.of(pick, new ItemStack(Items.DIAMOND)));
		helper.assertFalse(MaterialRepairRecipe.INSTANCE.matches(wrong, helper.getLevel()), "diamond doesn't repair iron");
		helper.succeed();
	}

	@GameTest
	public void netheriteRepairsWithDiamonds(GameTestHelper helper) {
		ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
		sword.setDamageValue(100);
		helper.assertTrue(MaterialRepairRecipe.INSTANCE.matches(
			CraftingInput.of(2, 1, List.of(sword, new ItemStack(Items.DIAMOND))), helper.getLevel()), "diamond repairs netherite");
		helper.assertFalse(MaterialRepairRecipe.INSTANCE.matches(
			CraftingInput.of(2, 1, List.of(sword, new ItemStack(Items.NETHERITE_INGOT))), helper.getLevel()), "ingots don't");
		helper.succeed();
	}
}
