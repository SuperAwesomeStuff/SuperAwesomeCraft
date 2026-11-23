package com.superawesomestuff.craft.client.gametest;

import com.superawesomestuff.craft.client.SlotEnchantingScreen;
import com.superawesomestuff.craft.enchanting.EnchantRules;
import net.fabricmc.fabric.api.client.gametest.v1.FabricClientGameTest;
import net.fabricmc.fabric.api.client.gametest.v1.context.ClientGameTestContext;
import net.fabricmc.fabric.api.client.gametest.v1.context.TestSingleplayerContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Opens the enchanting table in a real client and takes screenshots of the screen (empty, with a sword, after
 * enchanting). Run with {@code ./gradlew runClientGametest}; screenshots land in build/clientgametest/screenshots.
 */
public class EnchantingScreenClientTest implements FabricClientGameTest {
	@Override
	public void runTest(ClientGameTestContext context) {
		try (TestSingleplayerContext singleplayer = context.worldBuilder().create()) {
			singleplayer.getConnection().waitForChunksRender();
			singleplayer.getServer().runCommand("gamemode survival @a");
			singleplayer.getServer().runCommand("give @a minecraft:amethyst_shard 128");
			singleplayer.getServer().runCommand("give @a minecraft:rotten_flesh 64");
			singleplayer.getServer().runCommand("give @a minecraft:lapis_lazuli 40");
			singleplayer.getServer().runCommand("give @a minecraft:blaze_rod 8");

			singleplayer.getServer().runOnServer(server -> {
				ServerPlayer player = server.getPlayerList().getPlayers().getFirst();
				BlockPos pos = player.blockPosition().relative(player.getDirection(), 2);
				player.level().setBlockAndUpdate(pos, Blocks.ENCHANTING_TABLE.defaultBlockState());
				BlockState state = player.level().getBlockState(pos);
				state.useWithoutItem(player.level(), player, new BlockHitResult(Vec3.atCenterOf(pos), player.getDirection().getOpposite(), pos, false));
			});
			context.waitForScreen(SlotEnchantingScreen.class);
			context.takeScreenshot("enchanting-empty");

			singleplayer.getServer().runOnServer(server -> {
				ServerPlayer player = server.getPlayerList().getPlayers().getFirst();
				player.containerMenu.getSlot(0).set(new ItemStack(Items.DIAMOND_SWORD));
				player.containerMenu.broadcastChanges();
			});
			context.waitTicks(5);
			context.takeScreenshot("enchanting-sword");

			singleplayer.getServer().runOnServer(server -> {
				ServerPlayer player = server.getPlayerList().getPlayers().getFirst();
				var registry = server.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
				ItemStack sword = player.containerMenu.getSlot(0).getItem();
				for (int i = 0; i < 3; i++) {
					EnchantRules.addLevel(sword, registry.getOrThrow(Enchantments.SHARPNESS));
				}
				EnchantRules.addLevel(sword, registry.getOrThrow(Enchantments.SMITE));
				player.containerMenu.getSlot(0).setChanged();
				player.containerMenu.broadcastChanges();
			});
			context.waitTicks(5);
			context.takeScreenshot("enchanting-enchanted");
		}
	}
}
