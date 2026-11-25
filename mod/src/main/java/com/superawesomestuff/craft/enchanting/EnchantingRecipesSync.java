package com.superawesomestuff.craft.enchanting;

import com.superawesomestuff.craft.SuperAwesomeCraft;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/**
 * Loads the enchanting recipes config when the server starts and on {@code /reload}, and sends it to each player when they
 * join or the server reloads, so the enchanting screen shows the server's costs.
 */
public final class EnchantingRecipesSync {
	/** The server's recipe table, as the same JSON the config file uses. */
	public record Payload(String json) implements CustomPacketPayload {
		public static final Type<Payload> TYPE = new Type<>(SuperAwesomeCraft.id("enchanting_recipes"));
		public static final StreamCodec<RegistryFriendlyByteBuf, Payload> CODEC =
			ByteBufCodecs.stringUtf8(1 << 20).map(Payload::new, Payload::json).cast();

		@Override
		public Type<Payload> type() {
			return TYPE;
		}
	}

	private EnchantingRecipesSync() {
	}

	public static void init() {
		PayloadTypeRegistry.clientboundPlay().registerLarge(Payload.TYPE, Payload.CODEC, 1 << 20);
		ServerLifecycleEvents.SERVER_STARTING.register(server -> EnchantingRecipes.loadConfig());
		ServerLifecycleEvents.START_DATA_PACK_RELOAD.register((server, resources) -> EnchantingRecipes.loadConfig());
		ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
			if (ServerPlayNetworking.canSend(player, Payload.TYPE)) {
				ServerPlayNetworking.send(player, new Payload(EnchantingRecipes.toJson(EnchantingRecipes.current())));
			}
		});
	}
}
