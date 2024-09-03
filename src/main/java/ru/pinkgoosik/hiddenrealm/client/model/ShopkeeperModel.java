package ru.pinkgoosik.hiddenrealm.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import ru.pinkgoosik.hiddenrealm.entity.ShopkeeperEntity;

public class ShopkeeperModel extends EntityModel<ShopkeeperEntity> {
	private final ModelPart main;
	public ShopkeeperModel(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData parts = modelData.getRoot();
		parts.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, -48.0F, -16.0F, 32.0F, 48.0F, 32.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(ShopkeeperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		main.render(matrices, vertices, light, overlay);
	}
}
