package ru.pinkgoosik.hiddenrealm.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.hiddenrealm.extension.PlayerExtension;

import java.util.Set;

@Mixin(PlayerEntity.class)
abstract class PlayerMixin implements PlayerExtension {

	PlayerEntity self = (PlayerEntity)(Object)this;
	public PlayerExtension.PlayerPosition prevPosition = null;

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {

		if(prevPosition != null) {
			NbtCompound compound = new NbtCompound();
			compound.putDouble("x", prevPosition.pos.x);
			compound.putDouble("y", prevPosition.pos.y);
			compound.putDouble("z", prevPosition.pos.z);
			compound.putFloat("yaw", prevPosition.yaw);
			compound.putFloat("pitch", prevPosition.pitch);
			compound.putString("dimension", prevPosition.dimension.toString());

			nbt.put("PreviousPositionBeforeBazaar", compound);
		}
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		if(nbt.contains("PreviousPositionBeforeBazaar")) {
			NbtCompound compound = nbt.getCompound("PreviousPositionBeforeBazaar");
			prevPosition = new PlayerPosition(
				new Vec3d(compound.getDouble("x"), compound.getDouble("y"), compound.getDouble("z")),
				compound.getFloat("yaw"),
				compound.getFloat("pitch"),
				Identifier.of(compound.getString("dimension"))
			);
		}
	}

	@Override
	public void savePrevPosition() {
		prevPosition = new PlayerPosition(
			self.getPos(),
			self.getYaw(),
			self.getPitch(),
			self.getWorld().getRegistryKey().getValue()
		);
	}

	@Override
	public void teleportToPrevPosition() {
		if(prevPosition != null) {
			for(var world : self.getServer().getWorlds()) {
				if(world.getRegistryKey().getValue().equals(prevPosition.dimension)) {
					self.teleport(world, prevPosition.pos.x, prevPosition.pos.y + 0.5, prevPosition.pos.z, Set.of(), prevPosition.yaw, prevPosition.pitch);
					resetPrevPosition();
					break;
				}
			}
		}
	}

	@Override
	public void resetPrevPosition() {
		prevPosition = null;
	}

	@Override
	public void setPrevPosition(PlayerPosition pos) {
		this.prevPosition = pos;
	}

	@Override
	public PlayerPosition getPrevPosition() {
		return this.prevPosition;
	}
}
