package ru.pinkgoosik.hiddenrealm;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pinkgoosik.hiddenrealm.command.HiddenRealmCommands;
import ru.pinkgoosik.hiddenrealm.data.BazaarTrades;
import ru.pinkgoosik.hiddenrealm.event.HiddenRealmEvents;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlockEntities;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmBlocks;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEffects;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmEntities;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmItems;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmParticles;

public class HiddenRealmMod implements ModInitializer {
	public static final String MOD_ID = "hiddenrealm";
	public static final Logger LOGGER = LoggerFactory.getLogger("Hidden Realm");
	public static final RegistryKey<World> SILENT_BAZAAR = RegistryKey.of(RegistryKeys.WORLD, id("silent_bazaar"));

	@Override
	public void onInitialize() {
		HiddenRealmCommands.init();
		HiddenRealmEvents.init();
		HiddenRealmItems.init();
		HiddenRealmBlocks.init();
		HiddenRealmEntities.init();
		HiddenRealmBlockEntities.init();
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			HiddenRealmParticles.init();
		}
		HiddenRealmEffects.init();
		BazaarTrades.init();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
