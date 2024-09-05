package ru.pinkgoosik.hiddenrealm.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;
import ru.pinkgoosik.hiddenrealm.entity.LunarCoinEntity;
import ru.pinkgoosik.hiddenrealm.registry.HiddenRealmItems;

public class LunarCoinEntityRenderer extends EntityRenderer<LunarCoinEntity> {
	private final Random random = Random.create();
	private final ItemRenderer itemRenderer;
	private final float uniqueOffset = this.random.nextFloat() * 3.1415927F * 2.0F;

	protected LunarCoinEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		this.itemRenderer = ctx.getItemRenderer();
	}

	@Override
	public Identifier getTexture(LunarCoinEntity itemEntity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}

	@Override
	public void render(LunarCoinEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
		matrixStack.push();
		ItemStack itemStack = HiddenRealmItems.LUNAR_COIN.getDefaultStack();

		BakedModel bakedModel = this.itemRenderer.getModel(itemStack, entity.getWorld(),null, entity.getId());

		float j = MathHelper.sin(((float)entity.age + tickDelta) / 10.0F + uniqueOffset) * 0.1F + 0.1F;
		float k = bakedModel.getTransformation().getTransformation(ModelTransformationMode.GROUND).scale.y();
		matrixStack.translate(0.0F, j + 0.25F * k, 0.0F);
		float l = ((float)entity.age + tickDelta) / 20.0F + this.uniqueOffset;
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation(l));
		matrixStack.scale(1.5f,1.5f,1.5f);
		this.itemRenderer.renderItem(itemStack, ModelTransformationMode.GROUND, false, matrixStack, vertexConsumers, light, 0, bakedModel);
		matrixStack.pop();
		super.render(entity, yaw, tickDelta, matrixStack, vertexConsumers, light);
	}
}
