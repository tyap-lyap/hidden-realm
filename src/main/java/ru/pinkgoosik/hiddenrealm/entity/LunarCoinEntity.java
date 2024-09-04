package ru.pinkgoosik.hiddenrealm.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;

public class LunarCoinEntity extends Entity {
	public LunarCoinEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {

	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {

	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {

	}

	@Override
	public void tick() {
		super.tick();
		var list = this.getWorld().getNonSpectatingEntities(PlayerEntity.class,this.getBoundingBox());
		if(!list.isEmpty()){
			var player = list.get(this.getRandom().nextInt(list.size()));
			((LunarCoinExtension)player).addLunarCoin(1);
			this.discard();
		}
	}
}
