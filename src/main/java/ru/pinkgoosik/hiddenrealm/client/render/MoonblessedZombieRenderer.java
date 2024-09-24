package ru.pinkgoosik.hiddenrealm.client.render;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.client.HiddenRealmClient;
import ru.pinkgoosik.hiddenrealm.client.model.MoonblessedZombieModel;
import ru.pinkgoosik.hiddenrealm.entity.MoonblessedZombieEntity;

public class MoonblessedZombieRenderer extends MobEntityRenderer<MoonblessedZombieEntity, MoonblessedZombieModel> {
	private static final Identifier TEXTURE = Identifier.of(HiddenRealmMod.MOD_ID,"textures/entity/moonblessed_zombie.png");

	public MoonblessedZombieRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MoonblessedZombieModel(ctx.getPart(HiddenRealmClient.MOONBLESSED_ZOMBIE_LAYER)), 0.5F);
	}

	public Identifier getTexture(MoonblessedZombieEntity abstractSkeletonEntity) {
		return TEXTURE;
	}

}
