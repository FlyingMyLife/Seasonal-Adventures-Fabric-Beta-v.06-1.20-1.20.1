package net.packages.seasonal_adventures.block.entity.client;

import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.entity.EnergyInjectorBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class EnergyInjectorBlockModel extends GeoModel<EnergyInjectorBlockEntity> {

    @Override
    public Identifier getModelResource(EnergyInjectorBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "geo/energy_injector.geo.json");
    }

    @Override
    public Identifier getTextureResource(EnergyInjectorBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "textures/block/energy_injector.png");
    }

    @Override
    public Identifier getAnimationResource(EnergyInjectorBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "animations/energy_injector.animation.json");
    }
}
