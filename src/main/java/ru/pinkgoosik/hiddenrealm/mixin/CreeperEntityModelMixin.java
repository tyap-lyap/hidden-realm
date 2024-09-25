package ru.pinkgoosik.hiddenrealm.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.hiddenrealm.extension.LivingEntityExtension;

@Mixin(CreeperEntityModel.class)
abstract class CreeperEntityModelMixin<T extends Entity> extends SinglePartEntityModel<T> {

	@Shadow
	@Final
	private ModelPart head;

	@Inject(method = "setAngles", at = @At("HEAD"))
	void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
		if(entity instanceof LivingEntityExtension ex) {
			head.visible = !ex.isBeheaded();
		}
	}
}
