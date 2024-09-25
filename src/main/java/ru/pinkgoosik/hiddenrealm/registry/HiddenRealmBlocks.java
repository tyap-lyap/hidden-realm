package ru.pinkgoosik.hiddenrealm.registry;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.block.*;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.minecraft.block.AbstractBlock.Settings.copy;

@SuppressWarnings("unused")
public class HiddenRealmBlocks {
	public static final Map<Identifier, BlockItem> ITEMS = new LinkedHashMap<>();
	public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

	public static final Block BAZAAR_PORTAL_PAD = add("bazaar_portal_pad", new BazaarPortalPadBlock(copy(Blocks.OBSIDIAN).ticksRandomly().dropsNothing()));
	public static final Block BAZAAR_PORTAL = add("bazaar_portal", new BazaarPortalBlock(copy(Blocks.BEDROCK).replaceable().dropsNothing()));
	public static final Block TRADING_PEDESTAL = add("trading_pedestal", new TradingPedestalBlock(copy(Blocks.OBSIDIAN).nonOpaque()));
	public static final Block REFRESHER = add("refresher", new RefresherBlock(copy(Blocks.STONE).nonOpaque()));
	public static final Block LUNAR_FLAG = add("lunar_flag", new LunarFlagBlock(copy(Blocks.OAK_PLANKS).nonOpaque()));

	public static final Block MOONSTONE_BRICKS = add("moonstone_bricks", new Block(copy(Blocks.STONE_BRICKS)));
	public static final Block MOONSTONE_BRICK_STAIRS = add("moonstone_brick_stairs", new StairsBlock(MOONSTONE_BRICKS.getDefaultState(), copy(Blocks.STONE_BRICK_STAIRS)));
	public static final Block MOONSTONE_BRICK_SLAB = add("moonstone_brick_slab", new SlabBlock(copy(Blocks.STONE_BRICK_SLAB)));
	public static final Block MOONSTONE_BRICK_WALL = add("moonstone_brick_wall", new WallBlock(copy(Blocks.STONE_BRICK_WALL)));

	public static final Block MOSSY_MOONSTONE_BRICKS = add("mossy_moonstone_bricks", new Block(copy(Blocks.STONE_BRICKS)));
	public static final Block MOSSY_MOONSTONE_BRICK_STAIRS = add("mossy_moonstone_brick_stairs", new StairsBlock(MOSSY_MOONSTONE_BRICKS.getDefaultState(), copy(Blocks.STONE_BRICK_STAIRS)));
	public static final Block MOSSY_MOONSTONE_BRICK_SLAB = add("mossy_moonstone_brick_slab", new SlabBlock(copy(Blocks.STONE_BRICK_SLAB)));
	public static final Block MOSSY_MOONSTONE_BRICK_WALL = add("mossy_moonstone_brick_wall", new WallBlock(copy(Blocks.STONE_BRICK_WALL)));

	public static final Block CHISELED_MOONSTONE_BRICKS = add("chiseled_moonstone_bricks", new Block(copy(Blocks.STONE_BRICKS)));
//	public static final Block CRACKED_MOONSTONE_BRICKS = add("cracked_moonstone_bricks", new Block(copy(Blocks.STONE_BRICKS)));
	public static final Block SMOOTH_MOONSTONE = add("smooth_moonstone", new Block(copy(Blocks.STONE_BRICKS)));
	public static final Block SMOOTH_MOONSTONE_STAIRS = add("smooth_moonstone_stairs", new StairsBlock(SMOOTH_MOONSTONE.getDefaultState(), copy(Blocks.STONE_BRICKS)));
	public static final Block SMOOTH_MOONSTONE_SLAB = add("smooth_moonstone_slab", new SlabBlock(copy(Blocks.STONE_BRICK_SLAB)));

	public static final Block LUNAR_VINES = add("lunar_vines", new LunarVinesBlock(copy(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.VINE).noCollision().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
	public static final Block BOTTLE_WISP = add("bottle_wisp", new BottleWispBlock(copy(Blocks.GLASS).sounds(BlockSoundGroup.LANTERN).nonOpaque().luminance(state -> 15).pistonBehavior(PistonBehavior.DESTROY)));
	public static final Block LUNAR_MUSHROOM = add("lunar_mushroom", new LunarMushroomBlock(copy(Blocks.BROWN_MUSHROOM).sounds(BlockSoundGroup.FUNGUS).pistonBehavior(PistonBehavior.DESTROY)));
	public static final Block BAZAAR_LAMP = add("bazaar_lamp", new BazaarLampBlock(copy(Blocks.GLASS).sounds(BlockSoundGroup.LANTERN).nonOpaque().luminance(state -> 15).pistonBehavior(PistonBehavior.DESTROY)));
	public static final Block GUARDING_LAMP = add("guarding_lamp", new GuardingLampBlock(copy(Blocks.GLASS).sounds(BlockSoundGroup.LANTERN).nonOpaque().luminance(state -> state.get(GuardingLampBlock.ENABLED) ? 15 : 8).pistonBehavior(PistonBehavior.DESTROY)));

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
