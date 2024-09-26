package ru.pinkgoosik.hiddenrealm.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;

public class LunarCoinEntity extends Entity {
	private static final TrackedData<Integer> COUNT = DataTracker.registerData(LunarCoinEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public LunarCoinEntity(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		builder.add(COUNT, 1);
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		this.setCount(nbt.getInt("Count"));
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		nbt.putInt("Count", this.getCount());
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getWorld().isClient) {
			this.noClip = false;
		} else {
			this.noClip = !this.getWorld().isSpaceEmpty(this, this.getBoundingBox().contract(1.0E-7));
			if (this.noClip) {
				this.pushOutOfBlocks(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
			} else {
				setVelocity(0, 0, 0);
			}

			if (!this.isOnGround() || this.getVelocity().horizontalLengthSquared() > 9.999999747378752E-6 || (this.age + this.getId()) % 4 == 0) {
				this.move(MovementType.SELF, this.getVelocity());
				float f = 0.98F;

				this.setVelocity(this.getVelocity().multiply((double) f, 0.98, (double) f));
				if (this.isOnGround()) {
					Vec3d vec3d2 = this.getVelocity();
					if (vec3d2.y < 0.0) {
						this.setVelocity(vec3d2.multiply(1.0, -0.5, 1.0));
					}
				}
			}
		}

		var list = this.getWorld().getNonSpectatingEntities(PlayerEntity.class,this.getBoundingBox());
		if(!list.isEmpty()){
			var player = list.get(this.getRandom().nextInt(list.size()));
			((LunarCoinExtension)player).addLunarCoin(this.getCount());
			this.discard();
		}
	}

	public int getCount() {
		return this.dataTracker.get(COUNT);
	}

	public void setCount(int count) {
		this.dataTracker.set(COUNT, MathHelper.clamp(count,0,Integer.MAX_VALUE));
	}
}
