package ru.pinkgoosik.hiddenrealm.registry;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.block.GuardingLampBlock;
import ru.pinkgoosik.hiddenrealm.block.TradingPedestalBlock;
import ru.pinkgoosik.hiddenrealm.blockentity.GuardingLampBlockEntity;
import ru.pinkgoosik.hiddenrealm.blockentity.TradingPedestalBlockEntity;

import java.util.ArrayList;

public class HiddenRealmBlockEntities {

	public static final BlockEntityType<TradingPedestalBlockEntity> TRADING_PEDESTAL = Registry.register(
		Registries.BLOCK_ENTITY_TYPE,
		HiddenRealmMod.id("trading_pedestal"),
		BlockEntityType.Builder.create(TradingPedestalBlockEntity::new, collectPedestals()).build()
	);

	public static final BlockEntityType<GuardingLampBlockEntity> GUARDING_LAMP = Registry.register(
		Registries.BLOCK_ENTITY_TYPE,
		HiddenRealmMod.id("guarding_lamp"),
		BlockEntityType.Builder.create(GuardingLampBlockEntity::new, collectLamps()).build()
	);

	public static void init() {

	}

	public static TradingPedestalBlock[] collectPedestals() {
		ArrayList<TradingPedestalBlock> pedestals = new ArrayList<>();

		HiddenRealmBlocks.BLOCKS.forEach((id, block) -> {
			if(block instanceof TradingPedestalBlock pedestal) {
				pedestals.add(pedestal);
			}
		});

		return pedestals.toArray(new TradingPedestalBlock[0]);
	}

	public static GuardingLampBlock[] collectLamps() {
		ArrayList<GuardingLampBlock> lamps = new ArrayList<>();

		HiddenRealmBlocks.BLOCKS.forEach((id, block) -> {
			if(block instanceof GuardingLampBlock lamp) {
				lamps.add(lamp);
			}
		});

		return lamps.toArray(new GuardingLampBlock[0]);
	}
}
