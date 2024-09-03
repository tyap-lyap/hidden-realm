package ru.pinkgoosik.hiddenrealm.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.extension.LunarCoinExtension;
import ru.pinkgoosik.hiddenrealm.extension.PlayerExtension;

public class HiddenRealmEvents {

	public static void init() {

		ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
			((LunarCoinExtension)newPlayer).setLunarCoin(((LunarCoinExtension)oldPlayer).getLunarCoin());
		});

		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			var bazaar = server.getWorld(HiddenRealmMod.SILENT_BAZAAR);

			if(bazaar.getBlockState(new BlockPos(-32, 0, -32)).isAir()) {
				StructureTemplate structure = server.getStructureTemplateManager().getTemplateOrBlank(HiddenRealmMod.id("silent_bazaar"));
				StructurePlacementData data = new StructurePlacementData().setMirror(BlockMirror.NONE).setIgnoreEntities(true);
				structure.place(bazaar, new BlockPos(-32, 0, -32), new BlockPos(0, 0, 0), data, bazaar.getRandom(), 0);
			}
		});

		UseItemCallback.EVENT.register((player, world, hand) -> {
			if (!player.isCreative() && world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
				return TypedActionResult.fail(player.getStackInHand(hand));
			}

			return TypedActionResult.pass(player.getStackInHand(hand));
		});

		UseBlockCallback.EVENT.register((player, world, hand, res) -> {
			if (!player.isCreative() && !player.getStackInHand(hand).isEmpty() && world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
				return ActionResult.FAIL;
			}

			return ActionResult.PASS;
		});

		PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
			if (!player.isCreative() && world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
				return false;
			}

			return true;
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if(!player.isCreative() && world.getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue()) && entity instanceof PlayerEntity) {
				return ActionResult.FAIL;
			}
			return ActionResult.PASS;
		});

		ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
			if(entity instanceof ServerPlayerEntity player && player instanceof PlayerExtension ex) {

				if (player.getWorld().getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
					player.setHealth(20F);
					ex.teleportToPrevPosition();
					player.sendMessage(Text.translatable("message.hiddenrealm.silent_bazaar_death"));
					return false;
				}
			}

			return true;
		});
	}
}
