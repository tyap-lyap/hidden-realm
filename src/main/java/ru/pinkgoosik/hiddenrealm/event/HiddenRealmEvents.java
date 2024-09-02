package ru.pinkgoosik.hiddenrealm.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
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

			StructureTemplate structure = server.getStructureTemplateManager().getTemplateOrBlank(HiddenRealmMod.id("silent_bazaar"));
			StructurePlacementData data = new StructurePlacementData().setMirror(BlockMirror.NONE).setIgnoreEntities(true);
			structure.place(bazaar, new BlockPos(-32, 0, -32), new BlockPos(0, 0, 0), data, bazaar.getRandom(), 0);
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
