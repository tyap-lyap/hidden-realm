package ru.pinkgoosik.hiddenrealm.mixin.client;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.hiddenrealm.extension.LivingEntityExtension;

@Mixin(BipedEntityModel.class)
abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {

	@Shadow
	@Final
	public ModelPart head;

	@Shadow
	@Final
	public ModelPart hat;

	@Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
	void setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
		if(livingEntity instanceof LivingEntityExtension ex && !(livingEntity instanceof PlayerEntity)) {
			head.visible = !ex.isBeheaded();
			hat.visible = !ex.isBeheaded();
		}
	}
}
