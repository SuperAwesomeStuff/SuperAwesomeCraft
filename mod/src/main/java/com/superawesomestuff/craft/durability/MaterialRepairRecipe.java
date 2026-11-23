package com.superawesomestuff.craft.durability;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

/**
 * Repair in the crafting grid: a damaged item plus the material it's made from, in any arrangement. Each material item
 * restores half the item's durability. Enchantments and slot upgrades are kept. Never accepts more material than needed.
 * Netherite gear repairs with Diamonds, since Netherite Ingots are too dear to spend on repairs.
 */
public class MaterialRepairRecipe extends CustomRecipe {
	public static final MaterialRepairRecipe INSTANCE = new MaterialRepairRecipe();
	public static final MapCodec<MaterialRepairRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, MaterialRepairRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
	public static final RecipeSerializer<MaterialRepairRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	private record Found(ItemStack tool, int materials) {
	}

	public static int perMaterial(ItemStack tool) {
		return (tool.getMaxDamage() + 1) / 2;
	}

	private static @Nullable Found find(CraftingInput input) {
		ItemStack tool = null;
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (!stack.isEmpty() && stack.isDamageableItem() && stack.has(DataComponents.REPAIRABLE)) {
				if (tool != null) {
					return null;
				}
				tool = stack;
			}
		}
		if (tool == null || !tool.isDamaged() || tool.getCount() != 1) {
			return null;
		}
		Repairable repairable = tool.get(DataComponents.REPAIRABLE);
		int materials = 0;
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (stack.isEmpty() || stack == tool) {
				continue;
			}
			if (!isRepairMaterial(repairable, stack)) {
				return null;
			}
			materials++;
		}
		int needed = (tool.getDamageValue() + perMaterial(tool) - 1) / perMaterial(tool);
		if (materials == 0 || materials > needed) {
			return null;
		}
		return new Found(tool, materials);
	}

	public static boolean isRepairMaterial(Repairable repairable, ItemStack stack) {
		if (repairable.isValidRepairItem(new ItemStack(Items.NETHERITE_INGOT))) {
			return stack.is(Items.DIAMOND);
		}
		return repairable.isValidRepairItem(stack);
	}

	@Override
	public boolean matches(CraftingInput input, Level level) {
		return find(input) != null;
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		Found found = find(input);
		if (found == null) {
			return ItemStack.EMPTY;
		}
		ItemStack result = found.tool.copy();
		result.setDamageValue(Math.max(0, result.getDamageValue() - found.materials * perMaterial(result)));
		return result;
	}

	@Override
	public RecipeSerializer<MaterialRepairRecipe> getSerializer() {
		return SERIALIZER;
	}
}
