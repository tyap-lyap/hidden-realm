package ru.pinkgoosik.hiddenrealm.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEntities;

public abstract class MoonblessedEntity extends HostileEntity {
	private boolean isDisableDropCoin = false;

	protected MoonblessedEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
	}

	public int minDropCount(){
		return 3;
	}

	public int maxDropCount(){
		return 6;
	}

	public void setDisableDropCoin(boolean dropCoin){
		this.isDisableDropCoin = dropCoin;
	}

	public boolean isDisableDropCoin(){
		return isDisableDropCoin;
	}

	public void dropCoin(int min, int max){
		if(!this.isDisableDropCoin() && this.playerHitTimer > 0) {
			LunarCoinEntity lunarCoin = new LunarCoinEntity(HiddenRealmEntities.LUNAR_COIN, this.getWorld());
			lunarCoin.setPosition(this.getPos().add(this.getWorld().random.nextDouble(), this.getWorld().random.nextDouble(), this.getWorld().random.nextDouble()));
			lunarCoin.setCount(this.getRandom().nextBetween(min, max));
			this.getWorld().spawnEntity(lunarCoin);
		}
	}

	@Override
	public void onDeath(DamageSource damageSource) {
		super.onDeath(damageSource);
		this.dropCoin(this.minDropCount(),this.maxDropCount());
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("disableCoin", this.isDisableDropCoin());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setDisableDropCoin(nbt.getBoolean("disableCoin"));
	}
}
