package ru.pinkgoosik.hiddenrealm.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.hiddenrealm.extension.LivingEntityExtension;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmItems;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity implements LivingEntityExtension {

	private static final TrackedData<Boolean> BEHEADED = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "writeCustomDataToNbt", at = @At(value = "TAIL"))
	public  void writeCustomDataToNBT(NbtCompound nbt, CallbackInfo ci){
		nbt.putBoolean("Beheaded", this.isBeheaded());
	}

	@Inject(method = "readCustomDataFromNbt", at = @At(value = "TAIL"))
	public  void readCustomDataToNBT(NbtCompound nbt, CallbackInfo ci){
		this.setBeheaded(nbt.getBoolean("Beheaded"));
	}

	@Inject(method = "initDataTracker", at = @At(value = "TAIL"))
	public void initDataTracker(DataTracker.Builder builder, CallbackInfo ci) {
		builder.add(BEHEADED, false);
	}

	@Inject(method = "damage", at = @At("HEAD"))
	void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {

		if(source.getAttacker() instanceof PlayerEntity player) {
			if(player.getStackInHand(Hand.MAIN_HAND).isOf(HiddenRealmItems.BEHEADING_KATANA) && !isBeheaded()) {
				setBeheaded(true);
				dropStack(Items.ZOMBIE_HEAD.getDefaultStack());
			}
		}
	}

	@Override
	public boolean isBeheaded() {
		return dataTracker.get(BEHEADED);
	}

	@Override
	public void setBeheaded(boolean isBeheaded) {
		dataTracker.set(BEHEADED, isBeheaded);
	}
}
