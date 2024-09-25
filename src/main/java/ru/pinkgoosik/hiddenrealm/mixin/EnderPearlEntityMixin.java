package ru.pinkgoosik.hiddenrealm.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEffects;

@Mixin(EnderPearlEntity.class)
abstract class EnderPearlEntityMixin extends ThrownItemEntity {

	public EnderPearlEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "tick", at = @At(value = "TAIL"))
	void tick(CallbackInfo ci) {
		if(getOwner() instanceof ServerPlayerEntity player && player.hasStatusEffect(HiddenRealmEffects.GUARDING_LAMP_CURSE)) {
			this.discard();
		}
	}

}
