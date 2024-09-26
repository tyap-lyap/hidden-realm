package ru.pinkgoosik.hiddenrealm.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEffects;

@Mixin(EnderPearlItem.class)
abstract class EnderPearlItemMixin extends Item {

	public EnderPearlItemMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		if(!user.isCreative() && user.hasStatusEffect(HiddenRealmEffects.GUARDING_LAMP_CURSE)) cir.setReturnValue(super.use(world, user, hand));
	}
}
