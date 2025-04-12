package ru.pinkgoosik.hiddenrealm.mixin.common;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.hiddenrealm.extension.LivingEntityExtension;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmItems;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity implements LivingEntityExtension {

	@Shadow
	public abstract boolean isDead();

	@Shadow
	protected abstract void drop(ServerWorld world, DamageSource damageSource);

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

	@Inject(method = "damage", at = @At("TAIL"))
	void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {

		if(source.getAttacker() instanceof PlayerEntity player) {
			if(player.getMainHandStack().isOf(HiddenRealmItems.BEHEADING_KATANA) && !isBeheaded() && isDead()) {

				if((Object)this instanceof PlayerEntity target) {
					if(random.nextInt(100) < 10) {
						ItemStack head = Items.PLAYER_HEAD.getDefaultStack();
						head.applyComponentsFrom(ComponentMap.builder().add(DataComponentTypes.PROFILE, new ProfileComponent(target.getGameProfile())).build());
						this.dropStack(head);
						setBeheaded(true);
					}
				}
				else if(getType().equals(EntityType.WITHER_SKELETON)) {
					if(random.nextInt(100) < 3) {
						setBeheaded(true);
						this.dropItem(Items.WITHER_SKELETON_SKULL);
					}
				}
				else if(random.nextInt(100) < 7) {
					setBeheaded(true);

					if(getType().equals(EntityType.CREEPER)) {
						this.dropItem(Items.CREEPER_HEAD);
					}
					else if (getType().equals(EntityType.ZOMBIE)) {
						this.dropItem(Items.ZOMBIE_HEAD);
					}
					else if (getType().equals(EntityType.PIGLIN)) {
						this.dropItem(Items.PIGLIN_HEAD);
					}
					else if (getType().equals(EntityType.SKELETON)) {
						this.dropItem(Items.SKELETON_SKULL);
					}
					else if (getType().equals(EntityType.ENDER_DRAGON)) {
						this.dropItem(Items.DRAGON_HEAD);
					}
				}
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
