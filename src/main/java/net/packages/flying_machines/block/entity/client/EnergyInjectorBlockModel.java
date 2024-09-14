package net.packages.flying_machines.block.entity.client;

import net.minecraft.util.Identifier;
import net.packages.flying_machines.block.entity.EnergyInjectorBlockEntity;
import net.packages.flying_machines.flying_machines;
import software.bernie.geckolib.model.GeoModel;

public class EnergyInjectorBlockModel extends GeoModel<EnergyInjectorBlockEntity> {

    @Override
    public Identifier getModelResource(EnergyInjectorBlockEntity animatable) {
        return new Identifier(flying_machines.MOD_ID, "geo/energy_injector.geo.json");
    }

    @Override
    public Identifier getTextureResource(EnergyInjectorBlockEntity animatable) {
        return new Identifier(flying_machines.MOD_ID, "textures/block/energy_injector.png");
    }

    @Override
    public Identifier getAnimationResource(EnergyInjectorBlockEntity animatable) {
        return new Identifier(flying_machines.MOD_ID, "animations/energy_injector.animation.json");
    }
}
