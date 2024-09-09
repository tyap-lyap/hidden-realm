package ru.pinkgoosik.hiddenrealm.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.item.FireBootsItem;
import ru.pinkgoosik.hiddenrealm.item.LunarCoinItem;

import java.util.LinkedHashMap;
import java.util.Map;

public class HiddenRealmItems {
	public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

	public static final Item LUNAR_COIN = add("lunar_coin", new LunarCoinItem(new Item.Settings()));
	public static final Item FIRE_BOOTS = add("fire_boots", new FireBootsItem(new Item.Settings().fireproof().maxCount(1)));

	public static final Item EXPERIENCE_NECKLACE = add("experience_necklace", new Item(new Item.Settings().maxCount(1).maxDamage(100)));

	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
		.displayName(Text.translatable("itemGroup.hiddenrealm.items"))
		.entries((ctx, entries) -> {
			HiddenRealmItems.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
			HiddenRealmBlocks.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
		})
		.icon(HiddenRealmBlocks.BAZAAR_PORTAL.asItem()::getDefaultStack).build();

	public static void init() {
		Registry.register(Registries.ITEM_GROUP, HiddenRealmMod.id("items"), ITEM_GROUP);
		ITEMS.forEach((id, item) -> Registry.register(Registries.ITEM, id, item));
	}

	private static Item add(String name, Item item) {
		ITEMS.put(HiddenRealmMod.id(name), item);
		return item;
	}
}
