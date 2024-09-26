package ru.pinkgoosik.hiddenrealm.data;

import net.minecraft.item.Item;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlocks;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmItems;

import java.util.ArrayList;

import static net.minecraft.item.Items.*;

public class BazaarTrades {
	public static final ArrayList<Trade> TRADES = new ArrayList<>();

	//Placeholder trades, TODO: replace with custom content, make trades configurable
	public static void init() {
		add(HiddenRealmBlocks.MOONSTONE_BRICKS.asItem(), 32, 5, true);
		add(HiddenRealmBlocks.SMOOTH_MOONSTONE.asItem(), 32, 5, true);
		add(HiddenRealmBlocks.BAZAAR_LAMP.asItem(), 4, 10, true);
		add(HiddenRealmItems.BEHEADING_KATANA, 1, 95, false);
		add(HiddenRealmItems.FIRE_BOOTS, 1, 64, false);
		add(HiddenRealmItems.EXPERIENCE_NECKLACE, 1, 54, false);
		add(TOTEM_OF_UNDYING, 1, 24, false);

		add(OCHRE_FROGLIGHT, 32, 19, false);
		add(OCHRE_FROGLIGHT, 8, 5, true);
		add(VERDANT_FROGLIGHT, 32, 19, false);
		add(VERDANT_FROGLIGHT, 8, 5, true);
		add(PEARLESCENT_FROGLIGHT, 32, 19, false);
		add(PEARLESCENT_FROGLIGHT, 8, 5, true);

		add(TRIDENT, 1, 84, false);
		add(OMINOUS_TRIAL_KEY, 1, 49, false);
		add(TRIAL_KEY, 1, 20, false);
		add(BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 80, false);
		add(COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 80, false);
		add(DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 80, false);
		add(BLACKSTONE, 32, 5, true);
		add(BLACKSTONE, 64, 10, true);
		add(BASALT, 32, 5, true);
		add(BASALT, 64, 10, true);
		add(END_CRYSTAL, 4, 24, false);

		add(SCRAPE_POTTERY_SHERD, 1, 24, false);
		add(BREWER_POTTERY_SHERD, 1, 24, false);
		add(ARCHER_POTTERY_SHERD, 1, 24, false);
		add(SKULL_POTTERY_SHERD, 1, 24, false);

		add(END_STONE, 32, 4, true);
		add(WITHER_SKELETON_SKULL, 3, 75, false);
		add(SKELETON_SKULL, 1, 20, false);
		add(EMERALD_ORE, 4, 20, true);
		add(COAL_ORE, 4, 12, true);
		add(BUDDING_AMETHYST, 4, 32, false);
		add(ENDER_CHEST, 1, 16, false);
		add(JUKEBOX, 1, 16, false);
		add(ENCHANTED_GOLDEN_APPLE, 1, 120, false);
		add(COPPER_BLOCK, 8, 8, true);
		add(COPPER_BLOCK, 64, 38, false);
		add(GOLDEN_CARROT, 16, 16, true);
		add(DIAMOND_AXE, 1, 32, false);
		add(DIAMOND_PICKAXE, 1, 32, false);
		add(DIAMOND_SWORD, 1, 32, false);
		add(DIAMOND_SHOVEL, 1, 28, false);

		add(DIAMOND_HELMET, 1, 42, false);
		add(DIAMOND_CHESTPLATE, 1, 63, false);
		add(DIAMOND_LEGGINGS, 1, 54, false);
		add(DIAMOND_BOOTS, 1, 34, false);

		add(GUNPOWDER, 16, 16, true);
		add(GUNPOWDER, 8, 8, true);
		add(BLUE_ICE, 4, 2, true);
		add(BLUE_ICE, 64, 32, false);

		//TODO: add secret advancement
		add(DEAD_BUSH, 1, 79, false);

		add(ECHO_SHARD, 8, 46, false);
		add(POISONOUS_POTATO, 4, 29, false);
		add(CRYING_OBSIDIAN , 8, 26, false);
	}

	private static void add(Item item, int count, int price, boolean renewable) {
		TRADES.add(new Trade(item, count, price, renewable));
	}
}
