package com.superawesomestuff.craft.enchanting;

import com.superawesomestuff.craft.SuperAwesomeCraft;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;

/**
 * What one level of each enchantment costs. Mirrors docs/season-1/enchanting/recipes.md: a pile of one
 * material per level, 32 of a common one or 4 of a rare one. Levels IV and V also cost {@link #BLAZE_RODS_PER_HIGH_LEVEL}.
 */
public final class EnchantingRecipes {
	public static final int COMMON = 32;
	public static final int RARE = 4;
	public static final int BLAZE_RODS_PER_HIGH_LEVEL = 4;
	public static final Cost CURSE_REMOVAL = Cost.of(Items.GOLD_INGOT, 32);

	private static final Map<Identifier, Cost> PER_LEVEL = new HashMap<>();

	static {
		common(Items.REDSTONE, "efficiency", "quick_charge");
		common(Items.IRON_INGOT, "unbreaking", "protection", "sac:magnetism", "sac:bulwark");
		common(Items.LAPIS_LAZULI, "fortune", "looting", "luck_of_the_sea", "sac:bane_of_illagers");
		common(Items.AMETHYST_SHARD, "sharpness", "power");
		common(Items.FLINT, "breach", "piercing");
		PER_LEVEL.put(Identifier.withDefaultNamespace("silk_touch"), Cost.of(ItemTags.WOOL, Items.WOOL.pick(net.minecraft.world.item.DyeColor.WHITE), COMMON));
		common(Items.STRING, "lure", "multishot");
		common(Items.COAL, "sac:smelting");
		common(Items.COPPER_INGOT, "sac:felling", "channeling");
		common(Items.GOLD_INGOT, "density");
		common(Items.BONE_MEAL, "sac:harvesting");
		common(Items.BONE, "sac:beheading");
		common(Items.ROTTEN_FLESH, "smite");
		common(Items.SPIDER_EYE, "bane_of_arthropods");
		common(Items.SLIME_BALL, "knockback", "punch", "sac:rebounding", "sac:step_assist");
		common(Items.FEATHER, "feather_falling");
		common(Items.LEATHER, "projectile_protection");
		common(Items.GUNPOWDER, "blast_protection");
		common(Items.CACTUS, "thorns");
		common(Items.IRON_NUGGET, "sweeping_edge");
		common(Items.KELP, "respiration", "aqua_affinity");
		common(Items.PRISMARINE_SHARD, "depth_strider", "impaling");
		common(Items.SNOWBALL, "frost_walker", "sac:frost_aspect");
		common(Items.SOUL_SOIL, "soul_speed");
		rare(Items.DIAMOND, "sac:excavating", "infinity");
		rare(Items.ENDER_PEARL, "sac:reach", "loyalty");
		rare(Items.BLAZE_POWDER, "fire_aspect", "flame", "fire_protection");
		rare(Items.BREEZE_ROD, "lunge", "wind_burst");
		rare(Items.ECHO_SHARD, "swift_sneak");
		rare(Items.NAUTILUS_SHELL, "riptide");
	}

	private EnchantingRecipes() {
	}

	/** The material for one level, or null if the enchantment can't be made at the table (Mending, curses). */
	public static @Nullable Cost perLevel(Identifier enchantment) {
		return PER_LEVEL.get(enchantment);
	}

	public static Cost blazeRods() {
		return Cost.of(Items.BLAZE_ROD, BLAZE_RODS_PER_HIGH_LEVEL);
	}

	private static void common(Item item, String... ids) {
		add(item, COMMON, ids);
	}

	private static void rare(Item item, String... ids) {
		add(item, RARE, ids);
	}

	private static void add(Item item, int count, String... ids) {
		for (String id : ids) {
			Identifier key = id.startsWith("sac:") ? SuperAwesomeCraft.id(id.substring(4)) : Identifier.withDefaultNamespace(id);
			PER_LEVEL.put(key, Cost.of(item, count));
		}
	}
}
