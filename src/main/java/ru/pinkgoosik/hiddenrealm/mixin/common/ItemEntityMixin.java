package ru.pinkgoosik.hiddenrealm.mixin.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmItems;

import java.util.UUID;

@Mixin(ItemEntity.class)
abstract class ItemEntityMixin extends Entity {

	public ItemEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow
	public abstract ItemStack getStack();

	@Shadow
	private int pickupDelay;

	@Shadow
	private @Nullable UUID owner;

	@Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
	void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
		var stack = getStack();
		if(stack.isOf(HiddenRealmItems.LUNAR_COIN)) {
			if (!getWorld().isClient) {
				ItemStack itemStack = this.getStack();
				Item item = itemStack.getItem();
				int i = itemStack.getCount();
				if (pickupDelay == 0 && (owner == null || this.owner.equals(player.getUuid()))) {

					((LunarCoinExtension)player).addLunarCoin(itemStack.getCount());
					player.sendPickup(this, i);
					this.discard();

					player.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), i);
					player.triggerItemPickedUpByEntityCriteria((ItemEntity)(Object)this);
				}
			}
			ci.cancel();
		}
	}
}
