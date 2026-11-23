package com.superawesomestuff.craft.client;

import com.superawesomestuff.craft.durability.MaterialRepairRecipe;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Repairable;
import org.jspecify.annotations.Nullable;

/** "Repair with: Iron Ingot" in the tooltip of anything the crafting grid can repair. */
public final class RepairTooltip {
	private RepairTooltip() {
	}

	public static @Nullable Component describe(ItemStack stack) {
		Repairable repairable = stack.get(DataComponents.REPAIRABLE);
		if (repairable == null || !stack.isDamageableItem()) {
			return null;
		}
		List<Item> items = new ArrayList<>();
		for (Holder<Item> holder : repairable.items()) {
			if (MaterialRepairRecipe.isRepairMaterial(repairable, new ItemStack(holder))) {
				items.add(holder.value());
			}
		}
		if (MaterialRepairRecipe.isRepairMaterial(repairable, new ItemStack(Items.DIAMOND)) && !items.contains(Items.DIAMOND)) {
			items.clear();
			items.add(Items.DIAMOND);
		}
		if (items.isEmpty()) {
			return null;
		}
		Component name = new ItemStack(items.getFirst()).getHoverName();
		Component shown = items.size() > 2
			? Component.translatable("superawesomecraft.durability.repair_any", name)
			: items.size() == 2
			? Component.empty().append(name).append(", ").append(new ItemStack(items.get(1)).getHoverName())
			: name;
		return Component.translatable("superawesomecraft.durability.repair", shown).withStyle(ChatFormatting.GRAY);
	}
}
