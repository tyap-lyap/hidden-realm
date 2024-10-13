package ru.pinkgoosik.hiddenrealm.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;

public class HiddenRealmParticles {

	public static final SimpleParticleType BAZAAR_PORTAL = add("bazaar_portal");

	public static void init() {
	}

	private static SimpleParticleType add(String name) {
        return Registry.register(Registries.PARTICLE_TYPE, HiddenRealmMod.id(name), FabricParticleTypes.simple());
	}
}
