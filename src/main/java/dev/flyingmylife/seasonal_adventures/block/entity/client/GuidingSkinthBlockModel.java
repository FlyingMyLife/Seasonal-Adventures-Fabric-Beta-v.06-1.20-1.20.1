package dev.flyingmylife.seasonal_adventures.block.entity.client;

import net.minecraft.util.Identifier;
import dev.flyingmylife.seasonal_adventures.SA;
import dev.flyingmylife.seasonal_adventures.block.entity.GuidingSkinthBlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class GuidingSkinthBlockModel extends GeoModel<GuidingSkinthBlockEntity> {
    @Override
    public Identifier getModelResource(GuidingSkinthBlockEntity guidingSkinthBlockEntity, @Nullable GeoRenderer<GuidingSkinthBlockEntity> geoRenderer) {
        return Identifier.of(SA.MOD_ID, "geo/guiding_skinth.geo.json");
    }

    @Override
    public Identifier getTextureResource(GuidingSkinthBlockEntity guidingSkinthBlockEntity, @Nullable GeoRenderer<GuidingSkinthBlockEntity> geoRenderer) {
        return Identifier.of(SA.MOD_ID, "textures/block/guiding_skinth.png");
    }

    @Override
    public Identifier getAnimationResource(GuidingSkinthBlockEntity animatable) {
        return Identifier.of(SA.MOD_ID, "animations/guiding_skinth.animation.json");
    }
}
