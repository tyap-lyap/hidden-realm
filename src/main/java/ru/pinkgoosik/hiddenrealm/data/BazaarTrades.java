package ru.pinkgoosik.hiddenrealm.data;

import net.minecraft.item.Item;
import java.util.ArrayList;

import static net.minecraft.item.Items.*;

public class BazaarTrades {
	public static final ArrayList<Trade> TRADES = new ArrayList<>();

	//Placeholder trades, TODO: replace with custom content, make trades configurable
	public static void init() {
		add(TOTEM_OF_UNDYING, 1, 24, false);
		add(FLINT, 16, 10, true);
		add(FLINT, 8, 5, true);
		add(OCHRE_FROGLIGHT, 16, 10, true);
		add(OCHRE_FROGLIGHT, 8, 5, true);
		add(VERDANT_FROGLIGHT, 16, 10, true);
		add(VERDANT_FROGLIGHT, 8, 5, true);
		add(PEARLESCENT_FROGLIGHT, 16, 10, true);
		add(PEARLESCENT_FROGLIGHT, 8, 5, true);
		add(ELYTRA, 1, 48, false);
		add(TRIDENT, 1, 64, false);
		add(OMINOUS_TRIAL_KEY, 1, 32, false);
		add(TRIAL_KEY, 1, 20, false);
		add(NETHERITE_HELMET, 1, 128, false);
		add(NETHERITE_CHESTPLATE, 1, 128, false);
		add(NETHERITE_LEGGINGS, 1, 128, false);
		add(NETHERITE_BOOTS, 1, 128, false);
		add(BOLT_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 80, false);
		add(COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 80, false);
		add(DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, 1, 80, false);
		add(BLACKSTONE, 32, 5, true);
		add(BLACKSTONE, 64, 10, true);
		add(END_CRYSTAL, 4, 24, false);
		add(SCRAPE_POTTERY_SHERD, 1, 24, false);
		add(BREWER_POTTERY_SHERD, 1, 24, false);
		add(ARCHER_POTTERY_SHERD, 1, 24, false);
		add(SKULL_POTTERY_SHERD, 1, 24, false);
		add(END_STONE, 32, 4, true);
		add(WITHER_SKELETON_SKULL, 3, 64, false);
		add(SKELETON_SKULL, 1, 20, false);
		add(DEEPSLATE_EMERALD_ORE, 4, 24, true);
		add(DEEPSLATE_COAL_ORE, 4, 24, true);
		add(BUDDING_AMETHYST, 4, 32, false);
		add(CACTUS, 16, 4, true);
		add(CACTUS, 8, 2, true);
		add(ENDER_CHEST, 1, 16, false);
		add(ENCHANTED_GOLDEN_APPLE, 1, 100, false);
		add(COPPER_BLOCK, 16, 16, true);
		add(GOLDEN_CARROT, 16, 16, true);
		add(DIAMOND_AXE, 1, 32, false);
		add(DIAMOND_PICKAXE, 1, 32, false);
		add(DIAMOND_SWORD, 1, 32, false);
		add(DIAMOND_SHOVEL, 1, 28, false);
		add(GUNPOWDER, 16, 16, true);
		add(GUNPOWDER, 8, 8, true);
	}

	private static void add(Item item, int count, int price, boolean renewable) {
		TRADES.add(new Trade(item, count, price, renewable));
	}
}
