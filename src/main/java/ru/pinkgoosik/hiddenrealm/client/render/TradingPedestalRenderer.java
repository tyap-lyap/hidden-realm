package ru.pinkgoosik.hiddenrealm.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Vector3f;
import ru.pinkgoosik.hiddenrealm.blockentity.TradingPedestalBlockEntity;

public class TradingPedestalRenderer<T extends TradingPedestalBlockEntity> implements BlockEntityRenderer<T> {

	@Override
	public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		var world = entity.getWorld();
		ItemStack stack = entity.sellingItem;

		if(world != null && !entity.isRemoved() && !stack.isEmpty()) {
			matrices.push();

			MinecraftClient client = MinecraftClient.getInstance();
			BakedModel model = client.getItemRenderer().getModel(stack, world, null, 0);
			Vector3f translate = model.getTransformation().ground.translation;

			if (stack.getItem() instanceof BlockItem) {
				matrices.translate(translate.x() + 0.5, translate.y() + 1.15, translate.z() + 0.5);
				matrices.scale(1.5F, 1.5F, 1.5F);
			}
			else {
				matrices.translate(translate.x() + 0.5, translate.y() + 1.25, translate.z() + 0.5);
				matrices.scale(1.25F, 1.25F, 1.25F);
			}

			float rotation = ((int) (MinecraftClient.getInstance().world.getTime() % 314) + tickDelta) / 25.0F + 6.0F;
			matrices.multiply(RotationAxis.POSITIVE_Y.rotation(rotation));
			client.getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light, overlay, model);

			matrices.pop();
		}
	}

}
