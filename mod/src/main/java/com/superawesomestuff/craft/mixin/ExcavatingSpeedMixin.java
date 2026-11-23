package com.superawesomestuff.craft.mixin;

import com.superawesomestuff.craft.enchanting.ToolAbilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Excavating digs 3x3 but slower, so it can never instant-mine. Crouching mines one block at normal speed. */
@Mixin(Player.class)
public abstract class ExcavatingSpeedMixin {
	@Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
	private void superawesomecraft$slowerExcavating(BlockState state, CallbackInfoReturnable<Float> cir) {
		Player player = (Player) (Object) this;
		if (ToolAbilities.excavates(player)) {
			cir.setReturnValue(cir.getReturnValue() * ToolAbilities.EXCAVATING_SPEED);
		}
	}
}
