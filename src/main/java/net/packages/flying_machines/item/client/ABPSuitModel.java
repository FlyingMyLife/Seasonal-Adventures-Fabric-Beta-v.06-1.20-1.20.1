package net.packages.flying_machines.item.client;

import net.minecraft.util.Identifier;
import net.packages.flying_machines.flying_machines;
import net.packages.flying_machines.item.custom.ABPSuitItem;
import software.bernie.geckolib.model.GeoModel;

public class ABPSuitModel extends GeoModel<ABPSuitItem> {
    @Override
    public Identifier getModelResource(ABPSuitItem animatable) {
        return new Identifier(flying_machines.MOD_ID, "geo/abp_suit.geo.json");
    }

    @Override
    public Identifier getTextureResource(ABPSuitItem animatable) {
        return new Identifier(flying_machines.MOD_ID, "textures/armor/abp_suit.png");
    }

    @Override
    public Identifier getAnimationResource(ABPSuitItem animatable) {
        return new Identifier(flying_machines.MOD_ID, "animations/abp_suit.animation.json");
    }
}
