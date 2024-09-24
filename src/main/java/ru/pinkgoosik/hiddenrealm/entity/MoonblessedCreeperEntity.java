package ru.pinkgoosik.hiddenrealm.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEntities;

import java.util.EnumSet;

public class MoonblessedCreeperEntity extends HostileEntity implements MoonblessedEntity{

	private static final TrackedData<Integer> FUSE_SPEED = DataTracker.registerData(MoonblessedCreeperEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private int lastFuseTime;
	private int currentFuseTime;
	private int fuseTime = 200;
	private int attackCooldown = 0;

	public MoonblessedCreeperEntity(EntityType<? extends MoonblessedCreeperEntity> entityType, World world) {
		super(entityType, world);
	}

	protected void initGoals() {
		this.goalSelector.add(1, new SwimGoal(this));
		this.goalSelector.add(2, new SporeAttackGoal(this));
		this.goalSelector.add(1, new FleeEntityGoal(this,PlayerEntity.class,10,1,1.3));
		//this.goalSelector.add(4, new FollowZombieGoal(this,1.2F));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 10.0F));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.targetSelector.add(1, new RevengeGoal(this, MoonblessedEntity.class));
		this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
	}

	public static DefaultAttributeContainer.Builder createCreeperAttributes() {
		return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
	}

	public int getAttackCooldown() {
		return attackCooldown;
	}

	public void setAttackCooldown(int attackCooldown) {
		this.attackCooldown = attackCooldown;
	}

	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(FUSE_SPEED, -1);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putShort("Fuse", (short)this.fuseTime);
		nbt.putInt("AttackCooldown", (short)this.attackCooldown);
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("Fuse", 99)) {
			this.fuseTime = nbt.getShort("Fuse");
		}
		if (nbt.contains("AttackCooldown", 99)) {
			this.attackCooldown = nbt.getInt("AttackCooldown");
		}

	}

	public void tick() {
		if (this.isAlive()) {
			this.lastFuseTime = this.currentFuseTime;

			int i = this.getFuseSpeed();
			if (i > 0 && this.currentFuseTime == 0) {
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
			}

			--attackCooldown;
			if (this.currentFuseTime >= this.fuseTime && this.attackCooldown <=0) {
				this.currentFuseTime = this.fuseTime;
				this.sporeExplode();
			}
		}

		super.tick();
	}

	@Override
	public void onDeath(DamageSource damageSource) {
		super.onDeath(damageSource);

		for(int i = 0; i < this.getRandom().nextBetween(1,3) ;++i){
			LunarCoinEntity lunarCoin = new LunarCoinEntity(HiddenRealmEntities.LUNAR_COIN,this.getWorld());
			lunarCoin.setPosition(this.getPos().add(this.getWorld().random.nextDouble(),this.getWorld().random.nextDouble(),this.getWorld().random.nextDouble()));

			this.getWorld().spawnEntity(lunarCoin);
		}
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_CREEPER_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_CREEPER_DEATH;
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

	private void sporeExplode() {
		if (!this.getWorld().isClient) {
			this.currentFuseTime = 0;

			SporeEntity sporeEntity = new SporeEntity(HiddenRealmEntities.SPORE, this.getWorld());
			sporeEntity.setRadius(10);
			sporeEntity.setDuration(400);
			sporeEntity.setPosition(this.getPos().add(0, (-sporeEntity.getRadius()) / 2, 0));
			this.getWorld().spawnEntity(sporeEntity);
			this.setAttackCooldown(200);

			for(int i = 0; i < this.getRandom().nextBetween(1,2); ++i){
				MoonblessedZombieEntity moonblessedZombie = new MoonblessedZombieEntity(HiddenRealmEntities.MOONBLESSED_ZOMBIE,this.getWorld());
				moonblessedZombie.setPosition(this.getPos());
				if(this.getTarget() != null ){
					moonblessedZombie.setTarget(this.getTarget());
				}
				this.getWorld().spawnEntity(moonblessedZombie);
			}

			for(int i = 0; i < this.getRandom().nextBetween(0,2); ++i){
				MoonblessedSkeletonEntity skeletonEntity = new MoonblessedSkeletonEntity(HiddenRealmEntities.MOONBLESSED_SKELETON,this.getWorld());
				if(this.getTarget() != null ){
					skeletonEntity.setTarget(this.getTarget());
				}
				skeletonEntity.setPosition(this.getPos());
				skeletonEntity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
				this.getWorld().spawnEntity(skeletonEntity);
			}
		}
	}

	public class SporeAttackGoal extends Goal {
		private final MoonblessedCreeperEntity creeper;

		@Nullable
		private LivingEntity target;

		public SporeAttackGoal(MoonblessedCreeperEntity creeper) {
			this.creeper = creeper;
			this.setControls(EnumSet.of(Control.MOVE));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.creeper.getTarget();
			return this.creeper.getFuseSpeed() > 0 || livingEntity != null  && this.creeper.squaredDistanceTo(livingEntity) < 512.0;
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
			if(this.creeper.getAttackCooldown() > 0){
				this.creeper.setFuseSpeed(-10);
			} else if (this.target == null) {
				this.creeper.setFuseSpeed(-10);
			} else if (!(this.creeper.squaredDistanceTo(this.target) < 1024)){
				this.creeper.setFuseSpeed(-10);
			} else if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(this.target)) {
				this.creeper.setFuseSpeed(-10);
			} else {
				this.creeper.setFuseSpeed(3);
			}
		}
	}

/*	public class FollowZombieGoal extends Goal {
		private final MoonblessedCreeperEntity moonblessedCreeper;
		@Nullable
		private MoonblessedZombieEntity moonblessedZombie;
		private final double speed;
		private int delay;

		public FollowZombieGoal(MoonblessedCreeperEntity moonblessedCreeper, double speed) {
			this.moonblessedCreeper = moonblessedCreeper;
			this.speed = speed;
		}

		public boolean canStart() {
				var list = this.moonblessedCreeper.getWorld().getNonSpectatingEntities(MoonblessedZombieEntity.class, this.moonblessedCreeper.getBoundingBox().expand(8.0, 4.0, 8.0));
				double d = Double.MAX_VALUE;
			if (!list.isEmpty()) {
				for (MoonblessedZombieEntity moonblessedZombie : list) {

					double e = this.moonblessedCreeper.squaredDistanceTo(moonblessedZombie);
					if (!(e > d)) {
						d = e;
					}

					if (d < 9.0) {
						return false;
					} else {
						this.moonblessedZombie = moonblessedZombie;
						return true;
					}
				}
			}
		return  false;
		}

		public boolean shouldContinue() {
			if (!this.moonblessedZombie.isAlive()) {
				return false;
			} else {
				double d = this.moonblessedCreeper.squaredDistanceTo(this.moonblessedZombie);
				return !(d < 9.0) && !(d > 256.0);
			}
		}

		public void start() {
			this.delay = 0;
		}

		public void stop() {
			this.moonblessedZombie = null;
		}

		public void tick() {
			if (--this.delay <= 0) {
				this.delay = this.getTickCount(10);
				this.moonblessedCreeper.getNavigation().startMovingTo(this.moonblessedZombie, this.speed);
			}
		}
	}*/
}
