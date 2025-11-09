package net.flyingmylife.seasonal_adventures.block.entity.client;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.CopperLCBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class CopperLCBlockRenderer extends GeoBlockRenderer<CopperLCBlockEntity> {
    public CopperLCBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new CopperLCBlockModel());
    }
}
