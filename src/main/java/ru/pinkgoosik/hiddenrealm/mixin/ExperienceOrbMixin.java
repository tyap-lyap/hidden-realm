package ru.pinkgoosik.hiddenrealm.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmItems;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbMixin {
	@ModifyArgs(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;repairPlayerGears(Lnet/minecraft/server/network/ServerPlayerEntity;I)I"))
	public void ChangeExp(Args args) {

		ServerPlayerEntity player = args.get(0);

		if (player.getInventory().getMainHandStack().getItem() == HiddenRealmItems.EXPERIENCE_NECKLACE) {
			player.getInventory().getMainHandStack().damage(1, player, EquipmentSlot.MAINHAND);
			args.set(1, (int) args.get(1) * 2);
		}
	}
}
