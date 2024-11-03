package net.packages.seasonal_adventures.block.entity.client;

import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLIronBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public class LockedChestLvLIronBlockModel extends GeoModel<LockedChestLvLIronBlockEntity> {

    @Override
    public Identifier getModelResource(LockedChestLvLIronBlockEntity animatable) {
    return new Identifier(SeasonalAdventures.MOD_ID, "geo/locked_chest.geo.json");
    }

    @Override
    public Identifier getTextureResource(LockedChestLvLIronBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "textures/block/iron_level_locked_chest.png");
    }

    @Override
    public Identifier getAnimationResource(LockedChestLvLIronBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "animations/locked_chest.animation.json");
    }
}
