package net.packages.seasonal_adventures.block.entity.client;

import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.entity.AnimatedBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class  AnimatedBlockModel extends GeoModel<AnimatedBlockEntity> {
    @Override
    public Identifier getModelResource(AnimatedBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "geo/animated_block.geo.json");
    }
    @Override
    public Identifier getTextureResource(AnimatedBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "textures/block/animated_block.png");
    }

    @Override
    public Identifier getAnimationResource(AnimatedBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "animations/animated_block.animation.json");
    }
}
