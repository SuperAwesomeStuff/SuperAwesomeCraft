package com.superawesomestuff.craft.enchanting;

import com.mojang.serialization.Codec;
import com.superawesomestuff.craft.SuperAwesomeCraft;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

public final class ModComponents {
	/** Bit set of {@link SlotUpgrade}s applied to an item. */
	public static final DataComponentType<Integer> SLOT_UPGRADES = Registry.register(
		BuiltInRegistries.DATA_COMPONENT_TYPE,
		SuperAwesomeCraft.id("slot_upgrades"),
		DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build()
	);

	private ModComponents() {
	}

	public static void init() {
	}
}
