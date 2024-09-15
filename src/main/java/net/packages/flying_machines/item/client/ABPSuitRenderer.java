package net.packages.flying_machines.item.client;

import net.packages.flying_machines.item.custom.ABPSuitItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ABPSuitRenderer extends GeoArmorRenderer<ABPSuitItem> {
    public ABPSuitRenderer() {
        super(new ABPSuitModel());
    }
}
