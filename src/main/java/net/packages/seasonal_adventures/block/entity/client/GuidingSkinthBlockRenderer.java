package net.packages.seasonal_adventures.block.entity.client;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.packages.seasonal_adventures.block.entity.GuidingSkinthBlockEntity;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GuidingSkinthBlockRenderer extends GeoBlockRenderer<GuidingSkinthBlockEntity> {
    public GuidingSkinthBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new GuidingSkinthBlockModel());
    }
}
