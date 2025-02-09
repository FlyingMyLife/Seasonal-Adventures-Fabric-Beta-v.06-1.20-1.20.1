package net.packages.seasonal_adventures.block.entity.client;

import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.entity.GuidingSkinthBlockEntity;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLIronBlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class GuidingSkinthBlockModel extends GeoModel<GuidingSkinthBlockEntity> {
    @Override
    public Identifier getModelResource(GuidingSkinthBlockEntity guidingSkinthBlockEntity, @Nullable GeoRenderer<GuidingSkinthBlockEntity> geoRenderer) {
        return Identifier.of(SeasonalAdventures.MOD_ID, "geo/guiding_skinth.geo.json");
    }

    @Override
    public Identifier getTextureResource(GuidingSkinthBlockEntity guidingSkinthBlockEntity, @Nullable GeoRenderer<GuidingSkinthBlockEntity> geoRenderer) {
        return Identifier.of(SeasonalAdventures.MOD_ID, "textures/block/guiding_skinth.png");
    }

    @Override
    public Identifier getAnimationResource(GuidingSkinthBlockEntity animatable) {
        return Identifier.of(SeasonalAdventures.MOD_ID, "animations/guiding_skinth.animation.json");
    }
}
