package net.packages.flying_machines.block.entity.client;

import net.minecraft.util.Identifier;
import net.packages.flying_machines.block.entity.AnimatedBlockEntity;
import net.packages.flying_machines.flying_machines;
import software.bernie.geckolib.model.GeoModel;

public class  AnimatedBlockModel extends GeoModel<AnimatedBlockEntity> {
    @Override
    public Identifier getModelResource(AnimatedBlockEntity animatable) {
        return new Identifier(flying_machines.MOD_ID, "geo/animated_block.geo.json");
    }
    @Override
    public Identifier getTextureResource(AnimatedBlockEntity animatable) {
        return new Identifier(flying_machines.MOD_ID, "textures/block/animated_block.png");
    }

    @Override
    public Identifier getAnimationResource(AnimatedBlockEntity animatable) {
        return new Identifier(flying_machines.MOD_ID, "animations/animated_block.animation.json");
    }
}
