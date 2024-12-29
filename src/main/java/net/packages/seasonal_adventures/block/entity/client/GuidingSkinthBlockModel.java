package net.packages.seasonal_adventures.block.entity.client;

import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.entity.GuidingSkinthBlockEntity;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLIronBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class GuidingSkinthBlockModel extends GeoModel<GuidingSkinthBlockEntity> {

    @Override
    public Identifier getModelResource(GuidingSkinthBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "geo/guiding_skinth.geo.json");
    }

    @Override
    public Identifier getTextureResource(GuidingSkinthBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "textures/block/guiding_skinth.png");
    }

    @Override
    public Identifier getAnimationResource(GuidingSkinthBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "animations/guiding_skinth.animation.json");
    }
}
