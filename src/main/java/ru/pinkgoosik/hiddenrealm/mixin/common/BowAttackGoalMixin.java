package ru.pinkgoosik.hiddenrealm.mixin.common;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import ru.pinkgoosik.hiddenrealm.entity.MoonblessedSkeletonEntity;

@Mixin(BowAttackGoal.class)
public class BowAttackGoalMixin {
	@Shadow
	@Final
	private HostileEntity actor;

	@WrapWithCondition(
		method = "tick",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/control/MoveControl;strafeTo(FF)V")
	)
	public boolean render(MoveControl instance, float forward, float sideways) {
		return (!(this.actor instanceof MoonblessedSkeletonEntity && ((MoonblessedSkeletonEntity) this.actor).isSnipe()));
	}
}
