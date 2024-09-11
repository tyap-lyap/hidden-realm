package ru.pinkgoosik.hiddenrealm.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import ru.pinkgoosik.hiddenrealm.entity.ShopkeeperEntity;

public class ShopkeeperModel extends EntityModel<ShopkeeperEntity> {
	private final ModelPart Legs;
	private final ModelPart Tail;
	private final ModelPart RightArm;
	private final ModelPart LeftArm;
	private final ModelPart Hat;
	private final ModelPart Head;
	private final ModelPart Stuff;

	public ShopkeeperModel(ModelPart root) {
		this.Legs = root.getChild("Legs");
		this.Tail = root.getChild("Tail");
		this.RightArm = root.getChild("RightArm");
		this.LeftArm = root.getChild("LeftArm");
		this.Hat = root.getChild("Hat");
		this.Head = root.getChild("Head");
		this.Stuff = root.getChild("Stuff");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Legs = modelPartData.addChild("Legs", ModelPartBuilder.create(), ModelTransform.pivot(11.0F, 24.0F, -4.0F));

		ModelPartData cube_r1 = Legs.addChild("cube_r1", ModelPartBuilder.create().uv(66, 86).cuboid(-1.0F, -9.0F, -9.0F, 13.0F, 9.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 0.0F, 0.6109F, 0.0F));

		ModelPartData cube_r2 = Legs.addChild("cube_r2", ModelPartBuilder.create().uv(0, 93).cuboid(-1.0F, -9.0F, -8.0F, 13.0F, 9.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, 14.0F, 0.0F, -0.6109F, 0.0F));

		ModelPartData Tail = modelPartData.addChild("Tail", ModelPartBuilder.create().uv(1, 35).cuboid(-10.0F, 5.0F, -10.0F, 20.0F, 18.0F, 19.0F, new Dilation(2.0F))
			.uv(61, 53).cuboid(-7.0F, -14.0F, -10.0F, 13.0F, 14.0F, 19.0F, new Dilation(0.0F))
			.uv(3, 150).cuboid(-11.0F, -1.0F, -11.0F, 21.0F, 4.0F, 21.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

		ModelPartData RightArm = modelPartData.addChild("RightArm", ModelPartBuilder.create(), ModelTransform.of(12.9987F, -4.8181F, -13.5F, 0.0F, 0.0F, -0.5236F));

		ModelPartData cube_r3 = RightArm.addChild("cube_r3", ModelPartBuilder.create().uv(101, 94).cuboid(-14.0F, 15.0F, -8.0F, 12.0F, 7.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(-5.7873F, -18.3466F, 2.5F, 0.0F, 0.0F, -0.7418F));

		ModelPartData cube_r4 = RightArm.addChild("cube_r4", ModelPartBuilder.create().uv(67, 105).cuboid(-5.0F, -4.0F, -6.0F, 7.0F, 26.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-5.7873F, -8.3466F, 2.5F, 0.0F, 0.0F, -0.7418F));

		ModelPartData LeftArm = modelPartData.addChild("LeftArm", ModelPartBuilder.create(), ModelTransform.of(0.0F, -9.0F, 15.0F, 0.0F, 0.0F, 0.6981F));

		ModelPartData cube_r5 = LeftArm.addChild("cube_r5", ModelPartBuilder.create().uv(39, 105).cuboid(-5.0F, -4.0F, -6.0F, 7.0F, 26.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

		ModelPartData cube_r6 = LeftArm.addChild("cube_r6", ModelPartBuilder.create().uv(106, 52).cuboid(-14.0F, 17.0F, -8.0F, 12.0F, 3.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -8.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

		ModelPartData Hat = modelPartData.addChild("Hat", ModelPartBuilder.create().uv(2, 2).cuboid(-12.5F, 3.0F, -14.5F, 27.0F, 3.0F, 29.0F, new Dilation(0.0F))
			.uv(60, 34).cuboid(-8.5F, 0.0F, -7.5F, 19.0F, 3.0F, 15.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, -32.0F, -0.5F));

		ModelPartData cube_r7 = Hat.addChild("cube_r7", ModelPartBuilder.create().uv(95, 112).cuboid(-5.5F, -2.0F, -5.5F, 11.0F, 4.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		ModelPartData Head = modelPartData.addChild("Head", ModelPartBuilder.create().uv(85, 0).cuboid(-6.5F, -5.0F, -16.0F, 16.0F, 11.0F, 15.0F, new Dilation(0.0F))
			.uv(0, 12).cuboid(9.5F, 0.0F, -11.0F, 2.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, -21.0F, 8.0F));

		ModelPartData Stuff = modelPartData.addChild("Stuff", ModelPartBuilder.create().uv(0, 112).cuboid(-1.0F, 11.0F, -2.0F, 3.0F, 36.0F, 3.0F, new Dilation(0.0F))
			.uv(113, 37).cuboid(-3.0F, 9.0F, -5.0F, 7.0F, 2.0F, 9.0F, new Dilation(0.0F))
			.uv(0, 0).cuboid(-2.0F, 1.0F, -3.0F, 5.0F, 7.0F, 5.0F, new Dilation(0.0F))
			.uv(113, 26).cuboid(-3.0F, -2.0F, -5.0F, 7.0F, 2.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(21.0F, -23.0F, -13.0F));
		return TexturedModelData.of(modelData, 256, 256);
	}

	@Override
	public void setAngles(ShopkeeperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//		this.Head.yaw = netHeadYaw * (float) (Math.PI / 180.0);
//		this.Head.pitch = headPitch * (float) (Math.PI / 180.0);
//
//		this.Hat.copyTransform(this.Head);

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		Legs.render(matrices, vertices, light, overlay, color);
		Tail.render(matrices, vertices, light, overlay, color);
		RightArm.render(matrices, vertices, light, overlay, color);
		LeftArm.render(matrices, vertices, light, overlay, color);
		Hat.render(matrices, vertices, light, overlay, color);
		Head.render(matrices, vertices, light, overlay, color);
		Stuff.render(matrices, vertices, light, overlay, color);
	}
}
