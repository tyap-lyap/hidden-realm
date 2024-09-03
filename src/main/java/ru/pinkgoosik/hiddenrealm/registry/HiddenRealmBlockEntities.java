package ru.pinkgoosik.hiddenrealm.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.block.TradingPedestalBlock;
import ru.pinkgoosik.hiddenrealm.blockentity.TradingPedestalBlockEntity;

import java.util.ArrayList;

public class HiddenRealmBlockEntities {

	public static final BlockEntityType<TradingPedestalBlockEntity> TRADING_PEDESTAL = Registry.register(
		Registries.BLOCK_ENTITY_TYPE,
		HiddenRealmMod.id("trading_pedestal"),
		BlockEntityType.Builder.create(TradingPedestalBlockEntity::new, collectPedestals()).build()
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
}
