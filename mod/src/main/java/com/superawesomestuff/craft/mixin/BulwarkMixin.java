package com.superawesomestuff.craft.mixin;

import com.superawesomestuff.craft.enchanting.ModEnchantments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Bulwark: blocking a melee hit with a Bulwark shield has a 15% chance per level to disarm the attacker for 5 seconds.
 * A player's weapon goes on cooldown; a mob gets Weakness strong enough that its hits do nothing.
 */
@Mixin(LivingEntity.class)
public abstract class BulwarkMixin {
	private static final float CHANCE_PER_LEVEL = 0.15F;
	private static final int DISARM_TICKS = 100;

	@Inject(method = "blockUsingItem", at = @At("HEAD"))
	private void superawesomecraft$disarm(ServerLevel level, LivingEntity attacker, DamageSource source, float damage, boolean wasShield, CallbackInfo ci) {
		LivingEntity self = (LivingEntity) (Object) this;
		ItemStack shield = self.getItemBlockingWith();
		if (shield == null || source.getDirectEntity() != attacker) {
			return;
		}
		int bulwark = ModEnchantments.level(level.registryAccess(), shield, ModEnchantments.BULWARK);
		if (bulwark <= 0 || level.getRandom().nextFloat() >= CHANCE_PER_LEVEL * bulwark) {
			return;
		}
		if (attacker instanceof Player player) {
			ItemStack weapon = player.getMainHandItem();
			if (!weapon.isEmpty()) {
				player.getCooldowns().addCooldown(weapon, DISARM_TICKS);
			}
		} else {
			attacker.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, DISARM_TICKS, 9));
		}
	}
}
