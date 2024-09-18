package ru.pinkgoosik.hiddenrealm.registry;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.client.particle.BazaarPortalParticle;

public class HiddenRealmParticles {

	public static final SimpleParticleType BAZAAR_PORTAL = add("bazaar_portal", BazaarPortalParticle.Factory::new);

	public static void init() {
	}

	private static SimpleParticleType add(String name, ParticleFactoryRegistry.PendingParticleFactory<SimpleParticleType> constructor) {
		var particle = Registry.register(Registries.PARTICLE_TYPE, HiddenRealmMod.id(name), FabricParticleTypes.simple());
		ParticleFactoryRegistry.getInstance().register(particle, constructor);
		return particle;
	}
}
