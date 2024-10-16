package net.packages.flying_machines.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.packages.flying_machines.entity.custom.DylanEntity;
import net.packages.flying_machines.flying_machines;

public class DylanRenderer extends MobEntityRenderer<DylanEntity, DylanModel<DylanEntity>> {
    private static final Identifier TEXTURE = new Identifier(flying_machines.MOD_ID, "textures/entity/dylan.png");
    private static final Identifier BROKEN_TEXTURE = new Identifier(flying_machines.MOD_ID, "textures/entity/broken_dylan.png");

    public DylanRenderer(EntityRendererFactory.Context context) {
        super(context, new DylanModel<>(context.getPart(ModelLayers.DYLAN)), 0.6f);
    }

    @Override
    public Identifier getTexture(DylanEntity entity) {
        if (entity.getBrokenTexture) {
            return BROKEN_TEXTURE;
        }
        return TEXTURE;
    }

    @Override
    public void render(DylanEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
            matrixStack.scale(1f, 1f, 1f);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
