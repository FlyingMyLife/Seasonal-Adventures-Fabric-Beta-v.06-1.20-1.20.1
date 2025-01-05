// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.packages.seasonal_adventures.entity.client.legacy;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.packages.seasonal_adventures.entity.animation.DylanAnimations;
import net.packages.seasonal_adventures.entity.custom.DylanEntity;
@Deprecated
public class DylanModel<T extends DylanEntity> extends SinglePartEntityModel<T> {
	private final ModelPart dylan;;
	private final ModelPart head;
	private final ModelPart body;

	public DylanModel(ModelPart dylan) {
		this.dylan = dylan.getChild("dylan");
		this.body = this.dylan.getChild("body");
		this.head = this.body.getChild("torso").getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData dylan = modelPartData.addChild("dylan", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = dylan.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(20, 32).cuboid(-4.0F, -12.0F, -3.0F, 8.0F, 12.0F, 0.0F, new Dilation(0.0F))
				.uv(16, 16).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -9.0F, 0.0F));

		ModelPartData lefthand = torso.addChild("lefthand", ModelPartBuilder.create().uv(40, 16).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(6.0F, -9.0F, 0.0F));

		ModelPartData righthand = torso.addChild("righthand", ModelPartBuilder.create().uv(32, 48).cuboid(-2.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.0F, -9.0F, 0.0F));

		ModelPartData head = torso.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -15.0F, 0.0F));

		ModelPartData headmodel = head.addChild("headmodel", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, 1.0F, -1.0F));

		ModelPartData head_r1 = headmodel.addChild("head_r1", ModelPartBuilder.create().uv(0, 4).cuboid(-7.0F, 0.0F, -3.0F, 1.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData head_r2 = headmodel.addChild("head_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -6.0F, -3.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F))
				.uv(27, 38).cuboid(-7.0F, 0.0F, -3.0F, 8.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData head_r3 = headmodel.addChild("head_r3", ModelPartBuilder.create().uv(7, 4).cuboid(-7.0F, 0.0F, -3.0F, 1.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData head_r4 = headmodel.addChild("head_r4", ModelPartBuilder.create().uv(3, 4).cuboid(-6.0F, 0.0F, -3.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData head_eye = headmodel.addChild("head_eye", ModelPartBuilder.create(), ModelTransform.pivot(-3.0F, -1.0F, 1.0F));

		ModelPartData head_r5 = head_eye.addChild("head_r5", ModelPartBuilder.create().uv(1, 4).cuboid(-6.0F, 0.0F, -3.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -1.0F, -1.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData head_r6 = head_eye.addChild("head_r6", ModelPartBuilder.create().uv(6, 5).cuboid(-6.0F, 0.0F, -3.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, -1.0F, -1.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData hood = head.addChild("hood", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData Bottom_r1 = hood.addChild("Bottom_r1", ModelPartBuilder.create().uv(48, 38).cuboid(-4.0F, -1.0F, 0.0F, 8.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.0F, 4.0F, -1.5708F, 0.0F, 0.0F));

		ModelPartData R4_r1 = hood.addChild("R4_r1", ModelPartBuilder.create().uv(52, 56).cuboid(-2.0F, 1.5F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 0.5F, 2.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData L4_r1 = hood.addChild("L4_r1", ModelPartBuilder.create().uv(52, 54).cuboid(-2.0F, 1.5F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.5F, 2.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData L3_r1 = hood.addChild("L3_r1", ModelPartBuilder.create().uv(52, 61).cuboid(-2.0F, 0.5F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.5F, 3.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData R3_r1 = hood.addChild("R3_r1", ModelPartBuilder.create().uv(52, 58).cuboid(-2.0F, 0.5F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 0.5F, 3.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData R2_r1 = hood.addChild("R2_r1", ModelPartBuilder.create().uv(56, 59).cuboid(-2.0F, -1.5F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 1.5F, 4.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData L2_r1 = hood.addChild("L2_r1", ModelPartBuilder.create().uv(60, 53).cuboid(-2.0F, -1.5F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 1.5F, 4.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData L1_r1 = hood.addChild("L1_r1", ModelPartBuilder.create().uv(56, 53).cuboid(-2.0F, -2.5F, -1.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 1.5F, 5.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData R1_r1 = hood.addChild("R1_r1", ModelPartBuilder.create().uv(60, 58).cuboid(-2.0F, -2.5F, -1.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 1.5F, 5.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData back_r1 = hood.addChild("back_r1", ModelPartBuilder.create().uv(42, 32).cuboid(-8.0F, -2.0F, 4.0F, 10.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData headphone = head.addChild("headphone", ModelPartBuilder.create().uv(32, 8).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(49, 12).cuboid(0.0F, -4.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(44, 12).cuboid(9.0F, -4.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(42, 0).cuboid(0.0F, -5.0F, -1.0F, 10.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(40, 4).cuboid(9.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -1.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData eyes = head.addChild("eyes", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData eyeballs = eyes.addChild("eyeballs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.025F));

		ModelPartData Reyeball = eyeballs.addChild("Reyeball", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F, 0.0F, -3.975F));

		ModelPartData eyeballR_r1 = Reyeball.addChild("eyeballR_r1", ModelPartBuilder.create().uv(0, 32).cuboid(-5.0F, -2.0F, -3.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(4.45F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData Leyeball = eyeballs.addChild("Leyeball", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, 0.0F, -3.975F));

		ModelPartData eyeballL_r1 = Leyeball.addChild("eyeballL_r1", ModelPartBuilder.create().uv(0, 34).cuboid(-5.5F, -2.0F, -3.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(5.05F, 1.0F, 3.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData eyebrows = eyes.addChild("eyebrows", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData Reyebrow = eyebrows.addChild("Reyebrow", ModelPartBuilder.create(), ModelTransform.pivot(-2.5F, -1.5F, -4.0F));

		ModelPartData eyebrowR_r1 = Reyebrow.addChild("eyebrowR_r1", ModelPartBuilder.create().uv(2, 32).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 0.0F, -0.025F, 0.0F, 0.0F, 1.5708F));

		ModelPartData Leyebrow = eyebrows.addChild("Leyebrow", ModelPartBuilder.create(), ModelTransform.pivot(2.5F, -1.5F, -4.0F));

		ModelPartData eyebrowL_r1 = Leyebrow.addChild("eyebrowL_r1", ModelPartBuilder.create().uv(4, 32).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 0.0F, -0.025F, 0.0F, 0.0F, 1.5708F));

		ModelPartData eyelids = eyes.addChild("eyelids", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData up = eyelids.addChild("up", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData eyelidL_r1 = up.addChild("eyelidL_r1", ModelPartBuilder.create().uv(8, 32).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -1.5F, -3.975F, 0.0F, 0.0F, 1.5708F));

		ModelPartData eyelidR_r1 = up.addChild("eyelidR_r1", ModelPartBuilder.create().uv(6, 32).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -1.5F, -3.975F, 0.0F, 0.0F, 1.5708F));

		ModelPartData down = eyelids.addChild("down", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.0F, 0.0F));

		ModelPartData eyelidL_r2 = down.addChild("eyelidL_r2", ModelPartBuilder.create().uv(6, 34).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.5F, -3.975F, 0.0F, 0.0F, 1.5708F));

		ModelPartData eyelidR_r2 = down.addChild("eyelidR_r2", ModelPartBuilder.create().uv(8, 34).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 0.5F, -3.975F, 0.0F, 0.0F, 1.5708F));

		ModelPartData rightleg = body.addChild("rightleg", ModelPartBuilder.create().uv(16, 48).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -7.0F, 0.0F));

		ModelPartData leftleg = body.addChild("leftleg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -7.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		dylan.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
	@Override
	public ModelPart getPart() {
		return dylan;
	}

	@Override
	public void setAngles(DylanEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(headYaw, headPitch);
		this.animateMovement(DylanAnimations.WALK, limbAngle, limbDistance, 2f, 2.5f);
		this.updateAnimation(entity.attackAnimationState , DylanAnimations.ATTACK, entity.age, 1f);
		this.updateAnimation(entity.idleAnimationState , DylanAnimations.IDLE, entity.age, 1f);
	}
	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
}