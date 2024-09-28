package net.packages.seasonal_adventures.item.client;

import net.packages.seasonal_adventures.item.custom.ABPSuitItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ABPSuitRenderer extends GeoArmorRenderer<ABPSuitItem> {
    public ABPSuitRenderer() {
        super(new ABPSuitModel());
    }
}
