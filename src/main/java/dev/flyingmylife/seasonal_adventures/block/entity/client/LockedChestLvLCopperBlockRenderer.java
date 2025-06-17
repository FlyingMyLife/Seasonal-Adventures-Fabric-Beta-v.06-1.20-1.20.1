package dev.flyingmylife.seasonal_adventures.block.entity.client;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import dev.flyingmylife.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LockedChestLvLCopperBlockRenderer extends GeoBlockRenderer<LockedChestLvLCopperBlockEntity> {
    public LockedChestLvLCopperBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new LockedChestLvLCopperBlockModel());
    }
}
