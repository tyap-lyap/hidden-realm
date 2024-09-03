package ru.pinkgoosik.hiddenrealm.registry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.block.BazaarPortalBlock;
import ru.pinkgoosik.hiddenrealm.block.TradingPedestalBlock;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.minecraft.block.AbstractBlock.Settings.copy;

@SuppressWarnings("unused")
public class HiddenRealmBlocks {
	public static final Map<Identifier, BlockItem> ITEMS = new LinkedHashMap<>();
	public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

	public static final Block BAZAAR_PORTAL = add("bazaar_portal", new BazaarPortalBlock(copy(Blocks.BEDROCK)));
	public static final Block TRADING_PEDESTAL = add("trading_pedestal", new TradingPedestalBlock(copy(Blocks.STONE).nonOpaque().notSolid()));

	public static void init() {
		ITEMS.forEach((id, item) -> Registry.register(Registries.ITEM, id, item));
		BLOCKS.forEach((id, block) -> Registry.register(Registries.BLOCK, id, block));
	}

	public static Block add(String name, Block block) {
		Item.Settings settings = new Item.Settings();
		return addBlockItem(name, block, new BlockItem(block, settings));
	}

	public static Block addBlockItem(String name, Block block, BlockItem item) {
		addBlock(name, block);
		if (item != null) {
			item.appendBlocks(Item.BLOCK_ITEMS, item);
			ITEMS.put(HiddenRealmMod.id(name), item);
		}
		return block;
	}

	public static Block addBlock(String name, Block block) {
		BLOCKS.put(HiddenRealmMod.id(name), block);
		return block;
	}
}
