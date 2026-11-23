package com.superawesomestuff.craft.mixin;

import com.superawesomestuff.craft.enchanting.SlotEnchantingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** The enchanting table opens the Season 1 slot enchanting screen instead of the random vanilla one. */
@Mixin(EnchantingTableBlock.class)
public abstract class EnchantingTableBlockMixin {
	@Inject(method = "getMenuProvider", at = @At("HEAD"), cancellable = true)
	private void superawesomecraft$slotEnchanting(BlockState state, Level level, BlockPos pos, CallbackInfoReturnable<MenuProvider> cir) {
		cir.setReturnValue(new SimpleMenuProvider(
			(containerId, inventory, player) -> new SlotEnchantingMenu(containerId, inventory, ContainerLevelAccess.create(level, pos)),
			Component.translatable("container.enchant")
		));
	}
}
