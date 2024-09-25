package ru.pinkgoosik.hiddenrealm.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEffects;

@Mixin(ChorusFruitItem.class)
abstract class ChorusFruitItemMixin extends Item {

	public ChorusFruitItemMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "finishUsing", at = @At("HEAD"), cancellable = true)
	void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		if(user.hasStatusEffect(HiddenRealmEffects.GUARDING_LAMP_CURSE)) cir.setReturnValue(super.finishUsing(stack, world, user));
	}
}
