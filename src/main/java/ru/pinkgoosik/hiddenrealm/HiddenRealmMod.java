package ru.pinkgoosik.hiddenrealm;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pinkgoosik.hiddenrealm.command.HiddenRealmCommands;

public class HiddenRealmMod implements ModInitializer {
	public static final String MOD_ID = "hiddenrealm";
	public static final Logger LOGGER = LoggerFactory.getLogger("Hidden Realm");

	public static final RegistryKey<World> SILENT_BAZAAR = RegistryKey.of(RegistryKeys.WORLD, id("silent_bazaar"));

	@Override
	public void onInitialize() {
		HiddenRealmCommands.init();
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
