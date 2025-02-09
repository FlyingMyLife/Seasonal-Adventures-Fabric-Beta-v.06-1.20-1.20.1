package net.packages.seasonal_adventures.block.entity.client;

import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class LockedChestLvLCopperBlockModel extends GeoModel<LockedChestLvLCopperBlockEntity> {
    @Override
    public Identifier getModelResource(LockedChestLvLCopperBlockEntity lockedChestLvLCopperBlockEntity, @Nullable GeoRenderer<LockedChestLvLCopperBlockEntity> geoRenderer) {
        return Identifier.of(SeasonalAdventures.MOD_ID, "geo/locked_chest.geo.json");
    }

    @Override
    public Identifier getTextureResource(LockedChestLvLCopperBlockEntity lockedChestLvLCopperBlockEntity, @Nullable GeoRenderer<LockedChestLvLCopperBlockEntity> geoRenderer) {
        return Identifier.of(SeasonalAdventures.MOD_ID, "textures/block/copper_level_locked_chest.png");
    }

    @Override
    public Identifier getAnimationResource(LockedChestLvLCopperBlockEntity animatable) {
        return Identifier.of(SeasonalAdventures.MOD_ID, "animations/locked_chest.animation.json");
    }
}
