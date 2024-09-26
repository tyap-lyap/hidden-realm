package ru.pinkgoosik.hiddenrealm.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.item.BeheadingKatanaItem;
import ru.pinkgoosik.hiddenrealm.item.FireBootsItem;
import ru.pinkgoosik.hiddenrealm.item.LunarCoinItem;
import ru.pinkgoosik.hiddenrealm.item.LunarCoinsPouchItem;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class HiddenRealmItems {
	public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

	public static final Item LUNAR_COIN = add("lunar_coin", new LunarCoinItem(new Item.Settings()));
	public static final Item LUNAR_COINS_POUCH = add("lunar_coins_pouch", new LunarCoinsPouchItem(new Item.Settings()));
	public static final Item FIRE_BOOTS = add("fire_boots", new FireBootsItem(new Item.Settings().fireproof().maxCount(1)));
	public static final Item EXPERIENCE_NECKLACE = add("experience_necklace", new Item(new Item.Settings().maxCount(1).maxDamage(128)));
	public static final Item BEHEADING_KATANA = add("beheading_katana", new BeheadingKatanaItem(new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.DIAMOND, 3, -2.4F)).maxCount(1)));

	public static final Item MOONBLESSED_CREEPER_SPAWN_EGG = add("moonblessed_creeper_spawn_egg", new SpawnEggItem(HiddenRealmEntities.MOONBLESSED_CREEPER, 3370882, 1252639, new Item.Settings()));
	public static final Item MOONBLESSED_ZOMBIE_SPAWN_EGG = add("moonblessed_zombie_spawn_egg", new SpawnEggItem(HiddenRealmEntities.MOONBLESSED_ZOMBIE, 7634319, 2172461, new Item.Settings()));
	public static final Item MOONBLESSED_SKELETON_SPAWN_EGG = add("moonblessed_skeleton_spawn_egg", new SpawnEggItem(HiddenRealmEntities.MOONBLESSED_SKELETON, 2964543, 6860485, new Item.Settings()));

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
