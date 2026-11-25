package com.superawesomestuff.craft.enchanting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.superawesomestuff.craft.SuperAwesomeCraft;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;

/**
 * What everything at the enchanting table costs. The defaults below mirror docs/season-1/enchanting/recipes.md: a pile of
 * one material per level, 32 of a common one or 4 of a rare one; levels IV and V also cost Blaze Rods.
 *
 * <p>The live table comes from {@code config/superawesomecraft/enchanting.json}, written with these defaults the first
 * time the server starts. The server reads it on start and on {@code /reload} and sends it to every player, so the
 * screen shows what the server charges.
 */
public final class EnchantingRecipes {
	public static final String FILE_NAME = "enchanting.json";
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private static final java.util.regex.Pattern COST_BLOCK = java.util.regex.Pattern.compile("\\{\\s*(\"tag\"[^\\n]*\\s*)?\"item\"[^\\n]*\\s*\"count\": \\d+\\s*}");
	private static final String README = "Costs at the enchanting table. 'enchantments' maps each enchantment to what ONE level costs; "
		+ "an enchantment missing from the list can't be made at the table. A cost is {\"item\": id, \"count\": n}, or "
		+ "{\"tag\": item tag, \"item\": item shown on screen, \"count\": n} to accept any item in a tag. 'high_levels' is "
		+ "added on top for every level from 'from_level' up. Edit, then run /reload or restart. Delete this file to get "
		+ "the defaults back.";

	/** Every cost the table charges. */
	public record Table(
		Map<Identifier, Cost> perLevel, int highLevelFrom, Cost highLevelCost, Cost curseRemoval,
		Map<SlotUpgrade, List<Cost>> upgrades
	) {
	}

	private static volatile Table current = defaults();

	private EnchantingRecipes() {
	}

	// Reading the live table

	/** The material for one level, or null if the enchantment can't be made at the table (Mending, curses). */
	public static @Nullable Cost perLevel(Identifier enchantment) {
		return current.perLevel.get(enchantment);
	}

	/** The first level that also costs {@link #highLevelCost()}. */
	public static int highLevelFrom() {
		return current.highLevelFrom;
	}

	public static Cost highLevelCost() {
		return current.highLevelCost;
	}

	public static Cost curseRemoval() {
		return current.curseRemoval;
	}

	public static List<Cost> upgradeCosts(SlotUpgrade upgrade) {
		return current.upgrades.getOrDefault(upgrade, List.of());
	}

	public static Table current() {
		return current;
	}

	public static void install(Table table) {
		current = table;
	}

	// The config file

	public static Path configFile() {
		return FabricLoader.getInstance().getConfigDir().resolve(SuperAwesomeCraft.MOD_ID).resolve(FILE_NAME);
	}

	/** Loads the config file, writing the defaults first if it's missing. A broken file keeps the current table. */
	public static void loadConfig() {
		Path file = configFile();
		try {
			if (Files.notExists(file)) {
				Files.createDirectories(file.getParent());
				Files.writeString(file, toJson(defaults()), StandardCharsets.UTF_8);
				SuperAwesomeCraft.LOGGER.info("Wrote default enchanting recipes to {}", file);
			}
			install(fromJson(Files.readString(file, StandardCharsets.UTF_8)));
			SuperAwesomeCraft.LOGGER.info("Loaded {} enchanting recipes from {}", current.perLevel.size(), file);
		} catch (IOException | RuntimeException e) {
			SuperAwesomeCraft.LOGGER.error("Couldn't read {}; keeping the previous enchanting recipes", file, e);
		}
	}

	// JSON

	public static String toJson(Table table) {
		JsonObject root = new JsonObject();
		root.addProperty("_readme", README);
		JsonObject high = new JsonObject();
		high.addProperty("from_level", table.highLevelFrom);
		high.add("cost", costToJson(table.highLevelCost));
		root.add("high_levels", high);
		root.add("curse_removal", costToJson(table.curseRemoval));
		JsonObject upgrades = new JsonObject();
		for (SlotUpgrade upgrade : SlotUpgrade.values()) {
			JsonArray costs = new JsonArray();
			table.upgrades.getOrDefault(upgrade, List.of()).forEach(c -> costs.add(costToJson(c)));
			upgrades.add(upgrade.key(), costs);
		}
		root.add("slot_upgrades", upgrades);
		JsonObject enchantments = new JsonObject();
		new TreeMap<>(table.perLevel).forEach((id, cost) -> enchantments.add(id.toString(), costToJson(cost)));
		root.add("enchantments", enchantments);
		// One cost per line, so the file reads like a table.
		return COST_BLOCK.matcher(GSON.toJson(root)).replaceAll(m -> m.group().replaceAll("\\s*\\n\\s*", " "));
	}

	/**
	 * Parses a table. Sections left out fall back to the defaults; a bad entry (unknown item, missing count) is skipped
	 * with a warning. Throws if the text isn't a JSON object.
	 */
	public static Table fromJson(String json) {
		JsonObject root = JsonParser.parseString(json).getAsJsonObject();
		Table fallback = defaults();

		int highFrom = fallback.highLevelFrom;
		Cost highCost = fallback.highLevelCost;
		if (root.has("high_levels")) {
			JsonObject high = root.getAsJsonObject("high_levels");
			if (high.has("from_level")) {
				highFrom = high.get("from_level").getAsInt();
			}
			if (high.has("cost")) {
				highCost = orElse(costFromJson(high.get("cost"), "high_levels.cost"), highCost);
			}
		}
		Cost curse = root.has("curse_removal")
			? orElse(costFromJson(root.get("curse_removal"), "curse_removal"), fallback.curseRemoval)
			: fallback.curseRemoval;

		Map<SlotUpgrade, List<Cost>> upgrades = new EnumMap<>(fallback.upgrades);
		if (root.has("slot_upgrades")) {
			JsonObject section = root.getAsJsonObject("slot_upgrades");
			for (SlotUpgrade upgrade : SlotUpgrade.values()) {
				if (section.has(upgrade.key())) {
					List<Cost> costs = new ArrayList<>();
					for (JsonElement element : section.getAsJsonArray(upgrade.key())) {
						Cost cost = costFromJson(element, "slot_upgrades." + upgrade.key());
						if (cost != null) {
							costs.add(cost);
						}
					}
					upgrades.put(upgrade, List.copyOf(costs));
				}
			}
		}

		Map<Identifier, Cost> perLevel;
		if (root.has("enchantments")) {
			perLevel = new TreeMap<>();
			for (Map.Entry<String, JsonElement> entry : root.getAsJsonObject("enchantments").entrySet()) {
				Identifier id = Identifier.tryParse(entry.getKey());
				Cost cost = id == null ? null : costFromJson(entry.getValue(), entry.getKey());
				if (id == null) {
					SuperAwesomeCraft.LOGGER.warn("Enchanting recipes: '{}' isn't a valid enchantment id", entry.getKey());
				} else if (cost != null) {
					perLevel.put(id, cost);
				}
			}
		} else {
			perLevel = fallback.perLevel;
		}
		return new Table(Map.copyOf(perLevel), highFrom, highCost, curse, Map.copyOf(upgrades));
	}

	private static JsonObject costToJson(Cost cost) {
		JsonObject json = new JsonObject();
		if (cost.tag() != null) {
			json.addProperty("tag", cost.tag().location().toString());
		}
		json.addProperty("item", BuiltInRegistries.ITEM.getKey(cost.display()).toString());
		json.addProperty("count", cost.count());
		return json;
	}

	private static @Nullable Cost costFromJson(JsonElement element, String where) {
		if (!element.isJsonObject()) {
			SuperAwesomeCraft.LOGGER.warn("Enchanting recipes: {} should be an object like {{\"item\": ..., \"count\": ...}}", where);
			return null;
		}
		JsonObject json = element.getAsJsonObject();
		Item item = json.has("item") ? item(json.get("item").getAsString()) : null;
		if (item == null) {
			SuperAwesomeCraft.LOGGER.warn("Enchanting recipes: {} has a missing or unknown item", where);
			return null;
		}
		int count = json.has("count") ? json.get("count").getAsInt() : 0;
		if (count < 1) {
			SuperAwesomeCraft.LOGGER.warn("Enchanting recipes: {} needs a count of at least 1", where);
			return null;
		}
		if (json.has("tag")) {
			Identifier tag = Identifier.tryParse(json.get("tag").getAsString().replaceFirst("^#", ""));
			if (tag == null) {
				SuperAwesomeCraft.LOGGER.warn("Enchanting recipes: {} has an invalid tag", where);
				return null;
			}
			return Cost.of(TagKey.create(Registries.ITEM, tag), item, count);
		}
		return Cost.of(item, count);
	}

	private static @Nullable Item item(String id) {
		Identifier key = Identifier.tryParse(id);
		return key == null ? null : BuiltInRegistries.ITEM.getOptional(key).orElse(null);
	}

	private static Cost orElse(@Nullable Cost cost, Cost fallback) {
		return cost != null ? cost : fallback;
	}

	// Defaults

	private static final int COMMON = 32;
	private static final int RARE = 4;

	public static Table defaults() {
		Map<Identifier, Cost> perLevel = new TreeMap<>();
		Defaults d = new Defaults(perLevel);
		d.common(Items.REDSTONE, "efficiency", "quick_charge");
		d.common(Items.IRON_INGOT, "unbreaking", "protection", "sac:magnetism", "sac:bulwark");
		d.common(Items.LAPIS_LAZULI, "fortune", "looting", "luck_of_the_sea", "sac:bane_of_illagers");
		d.common(Items.AMETHYST_SHARD, "sharpness", "power");
		d.common(Items.FLINT, "breach", "piercing");
		perLevel.put(Identifier.withDefaultNamespace("silk_touch"), Cost.of(ItemTags.WOOL, Items.WOOL.pick(DyeColor.WHITE), COMMON));
		d.common(Items.STRING, "lure", "multishot");
		d.common(Items.COAL, "sac:smelting");
		d.common(Items.COPPER_INGOT, "sac:felling", "channeling");
		d.common(Items.GOLD_INGOT, "density");
		d.common(Items.BONE_MEAL, "sac:harvesting");
		d.common(Items.BONE, "sac:beheading");
		d.common(Items.ROTTEN_FLESH, "smite");
		d.common(Items.SPIDER_EYE, "bane_of_arthropods");
		d.common(Items.SLIME_BALL, "knockback", "punch", "sac:rebounding", "sac:step_assist");
		d.common(Items.FEATHER, "feather_falling");
		d.common(Items.LEATHER, "projectile_protection");
		d.common(Items.GUNPOWDER, "blast_protection");
		d.common(Items.CACTUS, "thorns");
		d.common(Items.IRON_NUGGET, "sweeping_edge");
		d.common(Items.KELP, "respiration", "aqua_affinity");
		d.common(Items.PRISMARINE_SHARD, "depth_strider", "impaling");
		d.common(Items.SNOW_BLOCK, "frost_walker", "sac:frost_aspect");
		d.common(Items.SOUL_SOIL, "soul_speed");
		d.rare(Items.DIAMOND, "sac:excavating", "infinity");
		d.rare(Items.ENDER_PEARL, "sac:reach", "loyalty");
		d.rare(Items.BLAZE_POWDER, "fire_aspect", "flame", "fire_protection");
		d.rare(Items.BREEZE_ROD, "lunge", "wind_burst");
		d.rare(Items.ECHO_SHARD, "swift_sneak");
		d.rare(Items.NAUTILUS_SHELL, "riptide");

		Map<SlotUpgrade, List<Cost>> upgrades = new EnumMap<>(SlotUpgrade.class);
		upgrades.put(SlotUpgrade.OVERWORLD, List.of(Cost.of(Items.DIAMOND_BLOCK, 1), Cost.of(Items.GOLD_BLOCK, 1)));
		upgrades.put(SlotUpgrade.NETHER, List.of(Cost.of(Items.NETHER_STAR, 1)));
		upgrades.put(SlotUpgrade.END, List.of(Cost.of(Items.SHULKER_SHELL, 4)));

		return new Table(Map.copyOf(perLevel), 4, Cost.of(Items.BLAZE_ROD, 4), Cost.of(Items.GOLD_INGOT, 32), Map.copyOf(upgrades));
	}

	private record Defaults(Map<Identifier, Cost> perLevel) {
		void common(Item item, String... ids) {
			this.add(item, COMMON, ids);
		}

		void rare(Item item, String... ids) {
			this.add(item, RARE, ids);
		}

		private void add(Item item, int count, String... ids) {
			for (String id : ids) {
				Identifier key = id.startsWith("sac:") ? SuperAwesomeCraft.id(id.substring(4)) : Identifier.withDefaultNamespace(id);
				this.perLevel.put(key, Cost.of(item, count));
			}
		}
	}
}
