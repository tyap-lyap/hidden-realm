package ru.pinkgoosik.hiddenrealm.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.entity.LunarCoinEntity;
import ru.pinkgoosik.hiddenrealm.entity.ShopkeeperEntity;
import ru.pinkgoosik.hiddenrealm.entity.TestLunarCreeperEntity;

public class HiddenRealmEntities {

	public static final EntityType<ShopkeeperEntity> SHOPKEEPER = Registry.register(
		Registries.ENTITY_TYPE,
		HiddenRealmMod.id("shopkeeper"),
		EntityType.Builder.create(ShopkeeperEntity::new, SpawnGroup.AMBIENT).dimensions(2f, 2f).build("shopkeeper")
	);

	public static final EntityType<TestLunarCreeperEntity> TEST_LUNAR_CREEPER = Registry.register(
		Registries.ENTITY_TYPE,
		HiddenRealmMod.id("test_lunar_creeper"),
		EntityType.Builder.create(TestLunarCreeperEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.7F).maxTrackingRange(8).build("test_lunar_creeper")
	);

	public static final EntityType<LunarCoinEntity> LUNAR_COIN = Registry.register(
		Registries.ENTITY_TYPE,
		HiddenRealmMod.id("lunar_coin"),
		EntityType.Builder.create(LunarCoinEntity::new, SpawnGroup.MISC).dimensions(0.5f, 0.5f).build("lunar_coin")
	);

	public static void init() {
		FabricDefaultAttributeRegistry.register(SHOPKEEPER, PassiveEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16));

		FabricDefaultAttributeRegistry.register(TEST_LUNAR_CREEPER, TestLunarCreeperEntity.createCreeperAttributes());
	}
}
