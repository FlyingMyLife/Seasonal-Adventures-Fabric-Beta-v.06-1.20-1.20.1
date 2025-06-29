package net.flyingmylife.seasonal_adventures.block.entity.client;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.LockedChestLvLIronBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class LockedChestLvLIronBlockRenderer extends GeoBlockRenderer<LockedChestLvLIronBlockEntity> {
    public LockedChestLvLIronBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new LockedChestLvLIronBlockModel());
    }
}
