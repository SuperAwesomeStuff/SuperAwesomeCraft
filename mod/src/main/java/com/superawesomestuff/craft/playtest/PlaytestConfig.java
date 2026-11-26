package com.superawesomestuff.craft.playtest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.superawesomestuff.craft.SuperAwesomeCraft;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Settings for the playtest log, from {@code config/superawesomecraft/playtest-log.json}, written with the defaults on
 * first start. On by default on dedicated servers only (game test servers count), so singleplayer worlds don't write logs.
 */
record PlaytestConfig(boolean enabled, boolean singleplayer, int snapshotMinutes) {
	static final PlaytestConfig DEFAULTS = new PlaytestConfig(true, false, 5);
	private static final String FILE_NAME = "playtest-log.json";
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private static final String README = "The playtest log records enchanting, repairs, deaths, dimension changes and a regular "
		+ "snapshot of each player's gear and position into <world>/superawesomecraft/playtest/. 'enabled' turns it off; "
		+ "'singleplayer' also turns it on in singleplayer and LAN worlds; 'snapshot_minutes' is how often snapshots are taken. "
		+ "Restart the server after editing.";

	static PlaytestConfig load() {
		Path file = FabricLoader.getInstance().getConfigDir().resolve(SuperAwesomeCraft.MOD_ID).resolve(FILE_NAME);
		try {
			if (Files.notExists(file)) {
				Files.createDirectories(file.getParent());
				Files.writeString(file, toJson(DEFAULTS), StandardCharsets.UTF_8);
			}
			JsonObject json = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
			return new PlaytestConfig(
				json.has("enabled") ? json.get("enabled").getAsBoolean() : DEFAULTS.enabled,
				json.has("singleplayer") ? json.get("singleplayer").getAsBoolean() : DEFAULTS.singleplayer,
				json.has("snapshot_minutes") ? json.get("snapshot_minutes").getAsInt() : DEFAULTS.snapshotMinutes
			);
		} catch (IOException | RuntimeException e) {
			SuperAwesomeCraft.LOGGER.error("Couldn't read {}; using the default playtest log settings", file, e);
			return DEFAULTS;
		}
	}

	private static String toJson(PlaytestConfig config) {
		JsonObject json = new JsonObject();
		json.addProperty("_readme", README);
		json.addProperty("enabled", config.enabled);
		json.addProperty("singleplayer", config.singleplayer);
		json.addProperty("snapshot_minutes", config.snapshotMinutes);
		return GSON.toJson(json);
	}
}
