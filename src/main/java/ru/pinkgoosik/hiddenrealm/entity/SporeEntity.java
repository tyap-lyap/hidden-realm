package ru.pinkgoosik.hiddenrealm.entity;

import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SporeEntity extends Entity {

	private static final TrackedData<Float> RADIUS = DataTracker.registerData(SporeEntity.class, TrackedDataHandlerRegistry.FLOAT);
	private int duration;

	public SporeEntity(EntityType<?> type, World world) {
		super(type, world);
		this.duration = 200;
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		builder.add(RADIUS, 20.0F);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getWorld().isClient) {
			for(int i = 0; i < 3; ++i) {
				this.getWorld().addParticle(ParticleTypes.TRIAL_OMEN, this.getParticleX(1), this.getRandomBodyY() - 0.25, this.getParticleZ(1), (this.random.nextDouble() - 0.3), -this.random.nextDouble(), (this.random.nextDouble() - 0.3));
			}
		} else {

			var list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox());

			if (!list.isEmpty()) {
				for (LivingEntity entity : list) {
					if (entity instanceof MoonblessedEntity) {
						entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 200, 2));
						entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 0));
					} else {
						entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 3));
						entity.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 200, 1));
					}
				}
			}

			if (duration != 0) {
				float radius = this.getDataTracker().get(RADIUS);
				this.setRadius(radius + (-radius/duration));
				--duration;

			} else {
				this.remove(RemovalReason.DISCARDED);
				this.discard();
			}

		}
	}

	public PistonBehavior getPistonBehavior() {
		return PistonBehavior.IGNORE;
	}

	public EntityDimensions getDimensions(EntityPose pose) {
		return EntityDimensions.changing(this.getRadius(), this.getRadius());
	}

	public void calculateDimensions() {
		super.calculateDimensions();
		float radius = this.getDataTracker().get(RADIUS);

		this.setPosition(this.getX(), this.getPos().add(0,-(-radius/duration)/2,0).getY(), this.getZ());
	}

	public void onTrackedDataSet(TrackedData<?> data) {
		if (RADIUS.equals(data)) {
			this.calculateDimensions();
		}

		super.onTrackedDataSet(data);
	}

	public float getRadius() {
	return this.getDataTracker().get(RADIUS);
	}

	public void setRadius(float radius) {
		if (!this.getWorld().isClient) {
			this.getDataTracker().set(RADIUS, MathHelper.clamp(radius, 0.0F, 32.0F));
		}
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		this.duration = nbt.getInt("Duration");
		this.setRadius(nbt.getFloat("Radius"));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putInt("Duration", this.duration);
		nbt.putFloat("Radius",this.getRadius());
	}
}
