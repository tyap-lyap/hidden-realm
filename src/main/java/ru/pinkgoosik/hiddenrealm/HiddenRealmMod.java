package ru.pinkgoosik.hiddenrealm;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pinkgoosik.hiddenrealm.command.HiddenRealmCommands;
import ru.pinkgoosik.hiddenrealm.data.BazaarTrades;
import ru.pinkgoosik.hiddenrealm.event.HiddenRealmEvents;
import ru.pinkgoosik.hiddenrealm.registry.*;

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
		HiddenRealmParticles.init();
		HiddenRealmEffects.init();
		BazaarTrades.init();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
