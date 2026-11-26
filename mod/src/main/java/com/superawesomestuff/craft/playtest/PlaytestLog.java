package com.superawesomestuff.craft.playtest;

import com.google.gson.JsonObject;
import com.superawesomestuff.craft.SuperAwesomeCraft;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityLevelChangeEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

/**
 * Records how people play during a playtest, for the measures in docs/season-1/enchanting/README.md#playtest-measures
 * and the playtest dashboard. Vanilla already keeps stats and advancements per player; this adds what it can't see:
 * what gets enchanted, removed, upgraded and repaired, when gear hits 0, where people die, which ability enchantments
 * get used, and a regular snapshot of each player's gear and position.
 *
 * <p>Each event is one JSON line in {@code <world>/superawesomecraft/playtest/<date>.jsonl}, with the time, the player
 * and a {@code type}. Systems record through {@link #event} for one-off actions and {@link #count} for frequent ones,
 * which are summed and written with the next snapshot. See {@link PlaytestConfig} for turning it off.
 */
public final class PlaytestLog {
	private static final List<Consumer<JsonObject>> LISTENERS = new CopyOnWriteArrayList<>();
	private static final Map<UUID, Map<String, Integer>> COUNTS = new ConcurrentHashMap<>();
	private static @Nullable JsonLinesWriter writer;
	private static PlaytestConfig config = PlaytestConfig.DEFAULTS;

	private PlaytestLog() {
	}

	public static void init() {
		ServerLifecycleEvents.SERVER_STARTED.register(PlaytestLog::start);
		// Stopped, not stopping: players are disconnected in between, and their leave events should be written.
		ServerLifecycleEvents.SERVER_STOPPED.register(server -> stop());
		ServerTickEvents.END_SERVER_TICK.register(PlaytestLog::tick);
		ServerPlayerEvents.JOIN.register(player -> event(player, "join", Snapshots.where(player)));
		ServerPlayerEvents.LEAVE.register(player -> {
			event(player, "leave", Snapshots.where(player));
			flushCounts(player);
		});
		ServerEntityLevelChangeEvents.AFTER_PLAYER_CHANGE_LEVEL.register((player, from, to) -> {
			JsonObject data = Snapshots.where(player);
			data.addProperty("from", from.dimension().identifier().toString());
			event(player, "change_dimension", data);
		});
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
			if (entity instanceof ServerPlayer player) {
				JsonObject data = Snapshots.where(player);
				data.addProperty("cause", source.typeHolder().unwrapKey().map(key -> key.identifier().toString()).orElse("unknown"));
				if (source.getEntity() != null) {
					data.addProperty("killer", net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(source.getEntity().getType()).toString());
				}
				event(player, "death", data);
			}
		});
	}

	// Recording

	/**
	 * Records one event for {@code player}. {@code data} holds the event's own fields; the time, player and type are added.
	 * Does nothing on the client.
	 */
	public static void event(Player player, String type, JsonObject data) {
		if (player.level().isClientSide()) {
			return;
		}
		JsonObject line = new JsonObject();
		line.addProperty("t", System.currentTimeMillis());
		line.addProperty("type", type);
		line.addProperty("player", player.getUUID().toString());
		line.addProperty("name", player.getPlainTextName());
		for (var entry : data.entrySet()) {
			line.add(entry.getKey(), entry.getValue());
		}
		for (Consumer<JsonObject> listener : LISTENERS) {
			listener.accept(line);
		}
		JsonLinesWriter current = writer;
		if (current != null) {
			current.write(line);
		}
	}

	/** Adds {@code amount} to one of {@code player}'s counters, written with their next snapshot. Does nothing on the client. */
	public static void count(Player player, String key, int amount) {
		if (player.level().isClientSide() || amount == 0) {
			return;
		}
		COUNTS.computeIfAbsent(player.getUUID(), id -> new ConcurrentHashMap<>()).merge(key, amount, Integer::sum);
	}

	/** An item as the log writes it: id, enchantments, slots, upgrades and damage. */
	public static JsonObject item(ItemStack stack) {
		return Snapshots.item(stack);
	}

	/** Sends every event to {@code listener} too, whether or not the file log is on. For tests. */
	public static void addListener(Consumer<JsonObject> listener) {
		LISTENERS.add(listener);
	}

	public static void removeListener(Consumer<JsonObject> listener) {
		LISTENERS.remove(listener);
	}

	// Lifecycle

	private static void start(MinecraftServer server) {
		config = PlaytestConfig.load();
		if (!config.enabled() || (!server.isDedicatedServer() && !config.singleplayer())) {
			return;
		}
		writer = new JsonLinesWriter(server.getWorldPath(net.minecraft.world.level.storage.LevelResource.ROOT)
			.resolve(SuperAwesomeCraft.MOD_ID).resolve("playtest").normalize());
		SuperAwesomeCraft.LOGGER.info("Playtest log on: writing to {}", writer.directory());
	}

	private static void stop() {
		JsonLinesWriter current = writer;
		writer = null;
		if (current != null) {
			current.close();
		}
		COUNTS.clear();
	}

	private static void tick(MinecraftServer server) {
		int every = Math.max(1, config.snapshotMinutes()) * 60 * 20;
		if (writer == null || server.getTickCount() % every != 0) {
			return;
		}
		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			JsonObject data = Snapshots.snapshot(player);
			Map<String, Integer> counts = COUNTS.remove(player.getUUID());
			if (counts != null) {
				data.add("counts", Snapshots.counts(counts));
			}
			event(player, "snapshot", data);
		}
	}

	private static void flushCounts(ServerPlayer player) {
		Map<String, Integer> counts = COUNTS.remove(player.getUUID());
		if (counts != null && !counts.isEmpty()) {
			JsonObject data = new JsonObject();
			data.add("counts", Snapshots.counts(counts));
			event(player, "counts", data);
		}
	}
}
