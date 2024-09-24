package ru.pinkgoosik.hiddenrealm.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.client.HiddenRealmClient;
import ru.pinkgoosik.hiddenrealm.client.model.MoonblessedCreeperModel;
import ru.pinkgoosik.hiddenrealm.entity.MoonblessedCreeperEntity;

public class MoonblessedCreeperRenderer extends MobEntityRenderer<MoonblessedCreeperEntity,MoonblessedCreeperModel>{
	private static final Identifier TEXTURE = Identifier.of(HiddenRealmMod.MOD_ID,"textures/entity/moonblessed_creeper.png");

	public MoonblessedCreeperRenderer(EntityRendererFactory.Context context) {
		super(context, new MoonblessedCreeperModel(context.getPart(HiddenRealmClient.MOONBLESSED_CREEPER_LAYER)), 0.5F);
	}

	protected void scale(MoonblessedCreeperEntity creeperEntity, MatrixStack matrixStack, float f) {
		float fuseTime = creeperEntity.getClientFuseTime(f);
		float scale = 1.0F + MathHelper.sin(fuseTime * 100.0F) * fuseTime * 0.01F;
		fuseTime = MathHelper.clamp(fuseTime, 0.0F, 1.0F);
		fuseTime *= fuseTime;
		fuseTime *= fuseTime;
		float scaleXZ = (1.0F + fuseTime * 0.4F) * scale;
		float scaleY = (1.0F + fuseTime * 0.1F) / scale;
		matrixStack.scale(scaleXZ, scaleY, scaleXZ);
	}

	@Override
	public void render(MoonblessedCreeperEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

		super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	protected float getAnimationCounter(MoonblessedCreeperEntity creeperEntity, float f) {
		float fuseTime = creeperEntity.getClientFuseTime(f);
		return (int)(fuseTime * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(fuseTime, 0.5F, 1.0F);
	}

	public Identifier getTexture(MoonblessedCreeperEntity creeperEntity) {
		return TEXTURE;
	}

}
