package ru.pinkgoosik.hiddenrealm.mixin;

import com.mojang.authlib.GameProfile;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.entity.FireTrailEntity;
import ru.pinkgoosik.hiddenrealm.extension.PlayerExtension;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEntities;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmItems;

@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerMixin extends PlayerEntity implements PlayerExtension {

	@Shadow
	public abstract ServerWorld getServerWorld();

	public ServerPlayerMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
		super(world, pos, yaw, gameProfile);
	}

	@Inject(method = "teleportTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;setServerWorld(Lnet/minecraft/server/world/ServerWorld;)V", shift = At.Shift.BEFORE))
	void teleportTo(TeleportTarget teleportTarget, CallbackInfoReturnable<Entity> cir) {
		if(!getWorld().getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue()) && teleportTarget.world().getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
			savePrevPosition();
		}
	}

	@Inject(method = "tick", at = @At("TAIL"))
	void tick(CallbackInfo ci) {
		var world = getServerWorld();
		var trinkets = TrinketsApi.getTrinketComponent(this);

		if(trinkets.isPresent()) {
			if(trinkets.get().isEquipped(HiddenRealmItems.FIRE_BOOTS) && this.isOnGround() && world.getServer().getTicks() % 5 == 0) {
				var trail = new FireTrailEntity(HiddenRealmEntities.FIRE_TRAIL, world);
				trail.owner = this.getUuid();
				trail.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
				world.spawnEntity(trail);
			}
		}
	}

}
