package ru.pinkgoosik.hiddenrealm.mixin.common;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.SpawnHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEntities;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {
	@Inject(method = "createMob", at = @At(value = "HEAD"), cancellable = true)
	private static void createMob(ServerWorld world, EntityType<?> type, CallbackInfoReturnable<MobEntity> cir){
		if(type == EntityType.CREEPER && world.getRandom().nextFloat() <= 0.05){
			cir.setReturnValue(HiddenRealmEntities.MOONBLESSED_CREEPER.create(world));
		}
		if(type == EntityType.ZOMBIE && world.getRandom().nextFloat() <= 0.05){
			cir.setReturnValue(HiddenRealmEntities.MOONBLESSED_ZOMBIE.create(world));
		}
		if(type == EntityType.SKELETON && world.getRandom().nextFloat() <= 0.05){
			cir.setReturnValue(HiddenRealmEntities.MOONBLESSED_SKELETON.create(world));
		}
	}
}
