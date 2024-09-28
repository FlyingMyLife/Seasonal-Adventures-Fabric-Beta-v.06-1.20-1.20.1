package net.packages.seasonal_adventures.block.entity.client;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.packages.seasonal_adventures.block.entity.EnergyInjectorBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EnergyInjectorBlockRenderer extends GeoBlockRenderer<EnergyInjectorBlockEntity> {
    public EnergyInjectorBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new EnergyInjectorBlockModel());
    }
}
