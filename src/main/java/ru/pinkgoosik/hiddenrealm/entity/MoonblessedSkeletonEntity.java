package ru.pinkgoosik.hiddenrealm.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MoonblessedSkeletonEntity extends HostileEntity implements RangedAttackMob,MoonblessedEntity {



	private boolean snipe;

	public MoonblessedSkeletonEntity(EntityType<? extends MoonblessedSkeletonEntity> entityType, World world) {
		super(entityType, world);
	}

	public boolean isSnipe(){
		return snipe;
	}

	public void setSnipe(boolean snipe) {
		this.snipe = snipe;
	}

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));

		return super.initialize(world, difficulty, spawnReason, entityData);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_SKELETON_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_SKELETON_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_SKELETON_DEATH;
	}

	protected void initGoals() {
		int speed = 0;
		if(!isSnipe()) {
			speed = 1;
			this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
		}
		this.goalSelector.add(4, new BowAttackGoal(this, speed, 20, 15.0F));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(6, new LookAroundGoal(this));
		this.targetSelector.add(1, new RevengeGoal(this, MoonblessedEntity.class));
		this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.isSnipe() && this.getTarget() != null) {
			this.getLookControl().lookAt(this.getTarget());
		}
	}

	@Override
	public void shootAt(LivingEntity target, float pullProgress) {
		ItemStack bow = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
		ItemStack arrowtype = this.getProjectileType(bow);
		PersistentProjectileEntity persistentProjectileEntity = ProjectileUtil.createArrowProjectile(this,arrowtype, pullProgress, bow);
		double x = target.getX() - this.getX();
		double y = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
		double z = target.getZ() - this.getZ();
		double distance = Math.sqrt(x * x + z * z);
		persistentProjectileEntity.setVelocity(x, y + distance * 0.20000000298023224, z, 1.6F, (float)(14 - this.getWorld().getDifficulty().getId() * 4));
		this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.getWorld().spawnEntity(persistentProjectileEntity);
	}
	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("isSnipe", this.isSnipe());
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.setSnipe(nbt.getBoolean("isSnipe"));
	}
}
