package ru.pinkgoosik.hiddenrealm.mixin;

import dev.emi.trinkets.api.TrinketsApi;
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
		var trinkets = TrinketsApi.getTrinketComponent(player);

		if(trinkets.isPresent() && trinkets.get().isEquipped(HiddenRealmItems.EXPERIENCE_NECKLACE)) {

			for(var pair : trinkets.get().getAllEquipped()) {
				var stack = pair.getRight();

				if(stack.isOf(HiddenRealmItems.EXPERIENCE_NECKLACE)) {
					if(stack.getDamage() != stack.getMaxDamage()) {
						stack.setDamage(stack.getDamage() + 1);
						args.set(1, (int) args.get(1) * 2);
						break;
					}
				}
			}
		}
	}
}
