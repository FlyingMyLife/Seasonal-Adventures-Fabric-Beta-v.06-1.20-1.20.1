package net.packages.flying_machines.block.entity.client;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.packages.flying_machines.block.entity.EnergyInjectorBlockEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class EnegryInjectorBlockRenderer extends GeoBlockRenderer<EnergyInjectorBlockEntity> {
    public EnegryInjectorBlockRenderer(BlockEntityRendererFactory.Context context) {
        super(new EnergyInjectorBlockModel());
    }
}
