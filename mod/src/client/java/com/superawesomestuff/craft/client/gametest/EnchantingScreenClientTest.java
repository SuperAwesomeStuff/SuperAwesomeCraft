package com.superawesomestuff.craft.client.gametest;

import com.superawesomestuff.craft.client.SlotEnchantingScreen;
import com.superawesomestuff.craft.enchanting.EnchantRules;
import com.superawesomestuff.craft.enchanting.SlotEnchantingMenu;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Opens the enchanting table in a real client and takes screenshots: empty, a sword with an enchantment selected,
 * and the materials filled in. Run with {@code ./gradlew runClientGametest}; screenshots land in
 * build/clientgametest/screenshots.
 */
public class EnchantingScreenClientTest implements FabricClientGameTest {
	@Override
	public void runTest(ClientGameTestContext context) {
		try (TestSingleplayerContext singleplayer = context.worldBuilder().create()) {
			singleplayer.getConnection().waitForChunksRender();
			singleplayer.getServer().runCommand("gamemode survival @a");
			singleplayer.getServer().runCommand("give @a minecraft:amethyst_shard 64");
			singleplayer.getServer().runCommand("give @a minecraft:lapis_lazuli 40");

			singleplayer.getServer().runOnServer(server -> {
				ServerPlayer player = server.getPlayerList().getPlayers().getFirst();
				BlockPos pos = player.blockPosition().relative(player.getDirection(), 2);
				player.level().setBlockAndUpdate(pos, Blocks.ENCHANTING_TABLE.defaultBlockState());
				player.level().getBlockState(pos).useWithoutItem(player.level(), player,
					new BlockHitResult(Vec3.atCenterOf(pos), player.getDirection().getOpposite(), pos, false));
			});
			context.waitForScreen(SlotEnchantingScreen.class);
			context.takeScreenshot("enchanting-empty");

			singleplayer.getServer().runOnServer(server -> {
				ServerPlayer player = server.getPlayerList().getPlayers().getFirst();
				ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
				var registry = server.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
				for (int i = 0; i < 3; i++) {
					EnchantRules.addLevel(sword, registry.getOrThrow(Enchantments.SHARPNESS));
				}
				player.containerMenu.getSlot(SlotEnchantingMenu.ITEM_SLOT).set(sword);
				player.containerMenu.broadcastChanges();
			});
			context.waitTicks(5);
			// Select the first tile (Bane of Arthropods).
			context.runOnClient(client -> {
				SlotEnchantingScreen screen = (SlotEnchantingScreen) client.gui.screen();
				int left = (screen.width - 256) / 2;
				int top = (screen.height - 222) / 2;
				screen.mouseClicked(new MouseButtonEvent(left + 70, top + 12, new MouseButtonInfo(0, 0)), false);
			});
			context.waitTicks(2);
			context.takeScreenshot("enchanting-selected");

			singleplayer.getServer().runOnServer(server -> {
				ServerPlayer player = server.getPlayerList().getPlayers().getFirst();
				var bane = server.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.BANE_OF_ARTHROPODS);
				player.containerMenu.getSlot(SlotEnchantingMenu.FIRST_MATERIAL_SLOT).set(
					net.minecraft.world.item.enchantment.EnchantmentHelper.createBook(new net.minecraft.world.item.enchantment.EnchantmentInstance(bane, 2)));
				player.containerMenu.broadcastChanges();
			});
			context.waitTicks(5);
			context.takeScreenshot("enchanting-materials");
		}
	}
}
