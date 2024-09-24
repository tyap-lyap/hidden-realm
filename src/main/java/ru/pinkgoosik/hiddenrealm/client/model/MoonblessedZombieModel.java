package ru.pinkgoosik.hiddenrealm.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.util.math.MatrixStack;
import ru.pinkgoosik.hiddenrealm.entity.MoonblessedZombieEntity;

public  class MoonblessedZombieModel extends BipedEntityModel<MoonblessedZombieEntity> {

	private final ModelPart headwear;
	private final ModelPart body_wear;

	public MoonblessedZombieModel(ModelPart root) {
		super(root, RenderLayer::getEntityTranslucent);
		this.headwear = root.getChild("headwear");
		this.body_wear = root.getChild("body_wear");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE,0);
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("headwear", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		modelPartData.addChild("body_wear", ModelPartBuilder.create().uv(12, 44).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	public void setAngles(MoonblessedZombieEntity moonblessedZombie, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setAngles(moonblessedZombie, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		this.headwear.copyTransform(this.head);
		this.body_wear.copyTransform(this.body);

		CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, this.isAttacking(moonblessedZombie), this.handSwingProgress, ageInTicks);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		super.render(matrices, vertices, light, overlay, color);
		this.headwear.render(matrices,vertices,light,overlay,color);
		this.body_wear.render(matrices,vertices,light,overlay,color);
	}

	public boolean isAttacking(MoonblessedZombieEntity zombieEntity) {
		return zombieEntity.isAttacking();
	}
}
