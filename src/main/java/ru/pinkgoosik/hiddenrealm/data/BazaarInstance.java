package ru.pinkgoosik.hiddenrealm.data;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.blockentity.TradingPedestalBlockEntity;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlocks;

import java.util.ArrayList;

public class BazaarInstance {

	public static BazaarInstance instance;

	public ArrayList<BlockPos> tradingPedestal;
	public MinecraftServer server;
	public int age = 0;

	public static void init(MinecraftServer server, ServerWorld bazaar) {
		instance = new BazaarInstance();
		instance.tradingPedestal = new ArrayList<>();
		instance.server = server;

		for (int y = 0; y <= 256; y++) {
			for (int x = 0; x <= 48; x++) {
				for (int z = 0; z <= 48; z++) {
					BlockPos pos = new BlockPos(-32 + x, y, -32 + z);

					if(bazaar.getBlockState(pos).isOf(HiddenRealmBlocks.TRADING_PEDESTAL)) {
						instance.tradingPedestal.add(pos);
					}
				}
			}
		}
	}

	public static void serverTick(MinecraftServer server) {
		instance.age++;

		if(instance.age % 24000 == 0) {
			refreshTrades();

			server.getPlayerManager().getPlayerList().forEach(player -> {
				if(player.getWorld().getRegistryKey().getValue().equals(HiddenRealmMod.SILENT_BAZAAR.getValue())) {
					player.sendMessage(Text.literal("Trades have been refreshed"));
				}
			});
		}

	}

	public static void refreshTrades() {
		var bazaar = instance.server.getWorld(HiddenRealmMod.SILENT_BAZAAR);

		instance.tradingPedestal.forEach(blockPos -> {
			if(bazaar.getBlockEntity(blockPos) instanceof TradingPedestalBlockEntity entity) {
				entity.refresh();
			}
		});
	}
}
