package ru.pinkgoosik.hiddenrealm.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEntities;

import java.util.EnumSet;

public class MoonblessedCreeperEntity extends HostileEntity implements MoonblessedEntity{

	private static final TrackedData<Integer> FUSE_SPEED = DataTracker.registerData(MoonblessedCreeperEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private int lastFuseTime;
	private int currentFuseTime;
	private int fuseTime = 200;

	public MoonblessedCreeperEntity(EntityType<? extends MoonblessedCreeperEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void initGoals() {
		this.goalSelector.add(1, new SwimGoal(this));
		this.goalSelector.add(2, new CreeperIgniteGoal(this));
		this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.targetSelector.add(1, new ActiveTargetGoal(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
	}

	public static DefaultAttributeContainer.Builder createCreeperAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}

	public int getSafeFallDistance() {
		return this.getTarget() == null ? this.getSafeFallDistance(0.0F) : this.getSafeFallDistance(this.getHealth() - 1.0F);
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		boolean bl = super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
		this.currentFuseTime += (int)(fallDistance * 1.5F);
		if (this.currentFuseTime > this.fuseTime - 5) {
			this.currentFuseTime = this.fuseTime - 5;
		}

		return bl;
	}

	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(FUSE_SPEED, -1);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);


		nbt.putShort("Fuse", (short)this.fuseTime);
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("Fuse", 99)) {
			this.fuseTime = nbt.getShort("Fuse");
		}

	}

	public void tick() {
		if (this.isAlive()) {
			this.lastFuseTime = this.currentFuseTime;


			int i = this.getFuseSpeed();
			if (i > 0 && this.currentFuseTime == 0) {
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
				this.emitGameEvent(GameEvent.PRIME_FUSE);
			}

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
			}

			if (this.currentFuseTime >= this.fuseTime) {
				this.currentFuseTime = this.fuseTime;
				this.explode();
			}
		}

		super.tick();
	}

	public void setTarget(@Nullable LivingEntity target) {
		if (!(target instanceof GoatEntity)) {
			super.setTarget(target);
		}
	}

	@Override
	public void onDeath(DamageSource damageSource) {
		super.onDeath(damageSource);
		int count = this.getWorld().getRandom().nextBetween(1,3);
		LunarCoinEntity lunarCoin = new LunarCoinEntity(HiddenRealmEntities.LUNAR_COIN,this.getWorld());
		
		for(int i = 0; i < count ;++i){

		}
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_CREEPER_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_CREEPER_DEATH;
	}

	protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
		super.dropEquipment(world, source, causedByPlayer);
		Entity entity = source.getAttacker();
		if (entity != this && entity instanceof CreeperEntity creeperEntity) {
			if (creeperEntity.shouldDropHead()) {
				creeperEntity.onHeadDropped();
				this.dropItem(Items.CREEPER_HEAD);
			}
		}

	}

	public boolean tryAttack(Entity target) {
		return true;
	}

	public float getClientFuseTime(float timeDelta) {
		return MathHelper.lerp(timeDelta, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
	}

	public int getFuseSpeed() {
		return (Integer)this.dataTracker.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(FUSE_SPEED, fuseSpeed);
	}

	private void explode() {
		if (!this.getWorld().isClient) {
		//	this.dead = true;
			this.fuseTime = 200;
			this.currentFuseTime = 0;
			//this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, World.ExplosionSourceType.MOB);
		//	this.onRemoval(Entity.RemovalReason.KILLED);
		//	this.discard();
		}
		SporeEntity sporeEntity = new SporeEntity(HiddenRealmEntities.SPORE,this.getWorld());
		sporeEntity.setPosition(this.getPos());
		sporeEntity.setRadius(20);
		sporeEntity.setDuration(600);
		this.getWorld().spawnEntity(sporeEntity);
	}

	public class CreeperIgniteGoal extends Goal {
		private final MoonblessedCreeperEntity creeper;
		@Nullable
		private LivingEntity target;

		public CreeperIgniteGoal(MoonblessedCreeperEntity creeper) {
			this.creeper = creeper;
			this.setControls(EnumSet.of(Control.MOVE));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.creeper.getTarget();
			return this.creeper.getFuseSpeed() > 0 || livingEntity != null && this.creeper.squaredDistanceTo(livingEntity) < 11.0;
		}

		public void start() {
			this.creeper.getNavigation().stop();
			this.target = this.creeper.getTarget();
		}

		public void stop() {
			this.target = null;
		}

		public boolean shouldRunEveryTick() {
			return true;
		}

		public void tick() {
			if (this.target == null) {
				this.creeper.setFuseSpeed(-10);
			} else if (this.creeper.squaredDistanceTo(this.target) > 49.0) {
				this.creeper.setFuseSpeed(-10);
			} else if (!this.creeper.getVisibilityCache().canSee(this.target)) {
				this.creeper.setFuseSpeed(-10);
			} else {
				this.creeper.setFuseSpeed(3);
			}
		}
	}
}
