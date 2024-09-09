package ru.pinkgoosik.hiddenrealm.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import ru.pinkgoosik.hiddenrealm.HiddenRealmMod;
import ru.pinkgoosik.hiddenrealm.client.HiddenRealmClient;
import ru.pinkgoosik.hiddenrealm.client.model.TestLunarCreeperModel;
import ru.pinkgoosik.hiddenrealm.entity.TestLunarCreeperEntity;

public class TestLunarCreeperRenderer extends MobEntityRenderer<TestLunarCreeperEntity, TestLunarCreeperModel<TestLunarCreeperEntity>> {
	private static final Identifier TEXTURE = Identifier.of(HiddenRealmMod.MOD_ID,"textures/entity/lunar_creeper/lunar_creeper.png");



	public TestLunarCreeperRenderer(EntityRendererFactory.Context context) {
		super(context, new TestLunarCreeperModel(context.getPart(HiddenRealmClient.TESTLUNARCREEPER_LAYER)), 0.5F);
	}

	protected void scale(TestLunarCreeperEntity creeperEntity, MatrixStack matrixStack, float f) {
		float g = creeperEntity.getClientFuseTime(f);
		float h = 1.0F + MathHelper.sin(g * 100.0F) * g * 0.01F;
		g = MathHelper.clamp(g, 0.0F, 1.0F);
		g *= g;
		g *= g;
		float i = (1.0F + g * 0.4F) * h;
		float j = (1.0F + g * 0.1F) / h;
		matrixStack.scale(i, j, i);
	}

	@Override
	public void render(TestLunarCreeperEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

		super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	protected float getAnimationCounter(TestLunarCreeperEntity creeperEntity, float f) {
		float g = creeperEntity.getClientFuseTime(f);
		return (int)(g * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(g, 0.5F, 1.0F);
	}

	public Identifier getTexture(TestLunarCreeperEntity creeperEntity) {
		return TEXTURE;
	}

}
