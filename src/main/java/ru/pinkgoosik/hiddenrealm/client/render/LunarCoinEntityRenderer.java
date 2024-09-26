package ru.pinkgoosik.hiddenrealm.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
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

import java.awt.*;

public class LunarCoinEntityRenderer extends EntityRenderer<LunarCoinEntity> {
	private final Random random = Random.create();
	private final ItemRenderer itemRenderer;
	private final float uniqueOffset = this.random.nextFloat() * 3.1415927F * 2.0F;
	private final TextRenderer textRenderer;

	protected LunarCoinEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx);
		this.itemRenderer = ctx.getItemRenderer();
		this.textRenderer = ctx.getTextRenderer();
	}

	@Override
	public Identifier getTexture(LunarCoinEntity itemEntity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}

	private void renderText(LunarCoinEntity entity,MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, boolean back){
		matrixStack.push();
		float f = 0.014F;

		if(back){
			matrixStack.translate(0.005,0.18,-0.017);
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation((float) Math.toRadians(180)));
			matrixStack.scale(f, -f, f);
		} else {
			matrixStack.translate(0.005,0.18,0.017);
			matrixStack.scale(f, -f, f);
		}

		String text = String.valueOf(entity.getCount());
		int wigth =  this.getTextRenderer().getWidth(text);
		this.textRenderer.draw(String.valueOf(entity.getCount()), (float)0-wigth/2, (float)0, new Color(0xFF23EAE0).getRGB(), true, matrixStack.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.NORMAL,0, 15728880);
		matrixStack.pop();
	}

	@Override
	public void render(LunarCoinEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
		matrixStack.push();
		ItemStack itemStack = HiddenRealmItems.LUNAR_COIN.getDefaultStack();

		BakedModel bakedModel = this.itemRenderer.getModel(itemStack, entity.getWorld(),null, entity.getId());

		float height = MathHelper.sin(((float)entity.age + tickDelta) / 10.0F + uniqueOffset) * 0.1F + 0.1F;
		float heightModel = bakedModel.getTransformation().getTransformation(ModelTransformationMode.GROUND).scale.y();
		matrixStack.translate(0.0F, height + 0.25F * heightModel, 0.0F);
		float rotation = ((float)entity.age + tickDelta) / 20.0F + this.uniqueOffset;
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotation(rotation));

		matrixStack.scale(1.5f,1.5f,1.5f);
		this.itemRenderer.renderItem(itemStack, ModelTransformationMode.GROUND, false, matrixStack, vertexConsumers, light, 0, bakedModel);
		this.renderText(entity,matrixStack,vertexConsumers,false);
		this.renderText(entity,matrixStack,vertexConsumers,true);
		matrixStack.pop();
		super.render(entity, yaw, tickDelta, matrixStack, vertexConsumers, light);
	}
}
