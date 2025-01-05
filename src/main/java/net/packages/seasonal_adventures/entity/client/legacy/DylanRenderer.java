package net.packages.seasonal_adventures.entity.client.legacy;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.entity.client.ModelLayers;
import net.packages.seasonal_adventures.entity.custom.DylanEntity;
@Deprecated
public class DylanRenderer extends MobEntityRenderer<DylanEntity, DylanModel<DylanEntity>> {
    private static final Identifier TEXTURE = new Identifier(SeasonalAdventures.MOD_ID, "textures/entity/deprecated_dylan.png");
    private static final Identifier BROKEN_TEXTURE = new Identifier(SeasonalAdventures.MOD_ID, "textures/entity/broken_dylan.png");

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
