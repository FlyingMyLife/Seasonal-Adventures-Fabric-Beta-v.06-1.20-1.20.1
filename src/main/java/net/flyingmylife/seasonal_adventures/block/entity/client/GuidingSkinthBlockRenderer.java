package net.flyingmylife.seasonal_adventures.block.entity.client;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.flyingmylife.seasonal_adventures.block.entity.GuidingSkinthBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GuidingSkinthBlockRenderer extends GeoBlockRenderer<GuidingSkinthBlockEntity> {
    public GuidingSkinthBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new GuidingSkinthBlockModel());
    }
}
