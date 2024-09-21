package ru.pinkgoosik.hiddenrealm.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;

public class HiddenRealmEffects {

	public static final RegistryEntry<StatusEffect> GUARDING_LAMP_CURSE = add("guarding_lamp_curse", new Effect(StatusEffectCategory.BENEFICIAL, 8171462));

	private static RegistryEntry<StatusEffect> add(String name, StatusEffect effect) {
		return Registry.registerReference(Registries.STATUS_EFFECT, HiddenRealmMod.id(name), effect);
	}

	public static void init() {
	}

	public static class Effect extends StatusEffect {

		public Effect(StatusEffectCategory category, int color) {
			super(category, color);
		}
	}
}
