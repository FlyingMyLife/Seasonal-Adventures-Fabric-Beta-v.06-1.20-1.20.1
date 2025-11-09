package net.flyingmylife.seasonal_adventures.block.entity.client;

import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.IronLCBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class IronLCBlockRenderer extends GeoBlockRenderer<IronLCBlockEntity> {
    public IronLCBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new IronLCBlockModel());
    }
}
