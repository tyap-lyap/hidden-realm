package ru.pinkgoosik.hiddenrealm.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FireTrailEntity extends Entity {
	public @Nullable UUID owner;

	public FireTrailEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {

	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		if(nbt.contains("ownerUuid")) owner = nbt.getUuid("ownerUuid");
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		if(owner != null) nbt.putUuid("ownerUuid", owner);
	}

	@Override
	public void tick() {
		super.tick();
		var list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox());

		if(!list.isEmpty()) {
			for (LivingEntity entity : list) {
				if(owner != null && !entity.getUuid().equals(owner)) entity.setOnFireFor(3);
			}
		}

		var pos = getPos();
		var rand = getWorld().getRandom();
		if(rand.nextInt(3) == 0 && getWorld() instanceof ServerWorld world) {
			world.spawnParticles(ParticleTypes.FLAME, pos.getX() + (rand.nextFloat() - 0.5F), pos.getY() + 0.25F, pos.getZ() + (rand.nextFloat() - 0.5F), 0, 0, 0, 0, 0);
		}

		if(age == 60) this.discard();
	}
}
