package ru.pinkgoosik.hiddenrealm.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements LunarCoinExtension {
	private static final TrackedData<Integer> LUNAR_COIN = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "writeCustomDataToNbt", at = @At(value = "TAIL"))
	public  void writeCustomDataToNBT(NbtCompound nbt, CallbackInfo ci){
		nbt.putInt("LunarCoin", this.getLunarCoin());
	}

	@Inject(method = "readCustomDataFromNbt", at = @At(value = "TAIL"))
	public  void readCustomDataToNBT(NbtCompound nbt, CallbackInfo ci){
		this.setLunarCoin(nbt.getInt("LunarCoin"));
	}

	@Inject(method = "initDataTracker", at = @At(value = "TAIL"))
	public void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
		builder.add(LUNAR_COIN, 0);
	}

	public int getLunarCoin(){
		return this.dataTracker.get(LUNAR_COIN);
	}

	public void setLunarCoin(int coin){
		 this.dataTracker.set(LUNAR_COIN,coin);
	}

	@Override
	public void addLunarCoin(int amount) {
		if (this.dataTracker.get(LUNAR_COIN) + amount >= 0) {
			this.dataTracker.set(LUNAR_COIN, this.dataTracker.get(LUNAR_COIN) + amount);
		}
	}
}
