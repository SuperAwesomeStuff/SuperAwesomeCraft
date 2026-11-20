package com.superawesomestuff.craft.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

/** Run with {@code ./gradlew runGametest}. Covers "No XP" from docs/vision.md#gear. */
public class NoExperienceTests {
	@GameTest
	public void orbsNeverSpawn(GameTestHelper helper) {
		Vec3 pos = helper.absoluteVec(new Vec3(4, 2, 4));
		ExperienceOrb.award(helper.getLevel(), pos, 100);
		helper.getLevel().addFreshEntity(new ExperienceOrb(helper.getLevel(), pos.x, pos.y, pos.z, 5));
		helper.assertEntityNotPresent(EntityTypes.EXPERIENCE_ORB);
		helper.succeed();
	}

	@GameTest
	public void playerKilledMobDropsNoOrbs(GameTestHelper helper) {
		Player player = helper.makeMockPlayer(GameType.SURVIVAL);
		Zombie zombie = helper.spawn(EntityTypes.ZOMBIE, new BlockPos(4, 2, 4));
		zombie.hurtServer(helper.getLevel(), helper.getLevel().damageSources().playerAttack(player), 1000);
		helper.assertTrue(zombie.isDeadOrDying(), "Zombie should be dead");
		helper.runAfterDelay(5, () -> {
			helper.assertEntityNotPresent(EntityTypes.EXPERIENCE_ORB);
			helper.succeed();
		});
	}

	@GameTest
	public void playersNeverGainExperience(GameTestHelper helper) {
		Player player = helper.makeMockPlayer(GameType.SURVIVAL);
		player.giveExperiencePoints(1000);
		player.giveExperienceLevels(30);
		helper.assertValueEqual(player.experienceLevel, 0, "experience level");
		helper.assertValueEqual(player.totalExperience, 0, "total experience");
		helper.succeed();
	}

	/** Technical parity: mobs keep their hidden XP value, so Sculk Catalysts still bloom and spread. */
	@GameTest(maxTicks = 200)
	public void sculkCatalystStillSpreads(GameTestHelper helper) {
		for (int x = 0; x < 8; x++) {
			for (int z = 0; z < 8; z++) {
				helper.setBlock(x, 0, z, Blocks.STONE);
			}
		}
		helper.setBlock(4, 1, 4, Blocks.SCULK_CATALYST);

		Player player = helper.makeMockPlayer(GameType.SURVIVAL);
		Zombie zombie = helper.spawn(EntityTypes.ZOMBIE, new BlockPos(3, 1, 3));
		zombie.hurtServer(helper.getLevel(), helper.getLevel().damageSources().playerAttack(player), 1000);

		helper.succeedWhen(() -> {
			for (int x = 0; x < 8; x++) {
				for (int z = 0; z < 8; z++) {
					if (helper.getBlockState(new BlockPos(x, 0, z)).is(Blocks.SCULK)) {
						return;
					}
				}
			}
			helper.fail("No sculk spread from the catalyst");
		});
	}

	@GameTest
	public void anvilRenamesForFree(GameTestHelper helper) {
		Player player = helper.makeMockPlayer(GameType.SURVIVAL);
		AnvilMenu menu = new AnvilMenu(0, player.getInventory());
		menu.getSlot(AnvilMenu.INPUT_SLOT).set(new ItemStack(Items.IRON_SWORD));
		menu.setItemName("Excalibur");

		ItemStack result = menu.getSlot(AnvilMenu.RESULT_SLOT).getItem();
		helper.assertValueEqual(result.getHoverName().getString(), "Excalibur", "renamed item");
		helper.assertValueEqual(menu.getCost(), 0, "anvil cost");
		helper.assertTrue(menu.getSlot(AnvilMenu.RESULT_SLOT).mayPickup(player), "Player with no XP should take the renamed item");
		helper.succeed();
	}

	@GameTest
	public void anvilCannotCombine(GameTestHelper helper) {
		Player player = helper.makeMockPlayer(GameType.SURVIVAL);
		AnvilMenu menu = new AnvilMenu(0, player.getInventory());
		ItemStack damaged = new ItemStack(Items.IRON_SWORD);
		damaged.setDamageValue(100);
		menu.getSlot(AnvilMenu.INPUT_SLOT).set(damaged);
		menu.getSlot(AnvilMenu.ADDITIONAL_SLOT).set(new ItemStack(Items.IRON_INGOT, 4));
		menu.setItemName("Excalibur");

		helper.assertTrue(menu.getSlot(AnvilMenu.RESULT_SLOT).getItem().isEmpty(), "Repairing or combining should give no result");
		helper.succeed();
	}
}
