package net.flyingmylife.seasonal_adventures.block.entity.client;

import net.flyingmylife.seasonal_adventures.SA;
import net.minecraft.util.Identifier;
import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.LockedChestLvLIronBlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class LockedChestLvLIronBlockModel extends GeoModel<LockedChestLvLIronBlockEntity> {

    @Override
    public Identifier getModelResource(LockedChestLvLIronBlockEntity lockedChestLvLIronBlockEntity, @Nullable GeoRenderer<LockedChestLvLIronBlockEntity> geoRenderer) {
        return Identifier.of(SA.MOD_ID, "geo/locked_chest.geo.json");
    }

    @Override
    public Identifier getTextureResource(LockedChestLvLIronBlockEntity lockedChestLvLIronBlockEntity, @Nullable GeoRenderer<LockedChestLvLIronBlockEntity> geoRenderer) {
        return Identifier.of(SA.MOD_ID, "textures/block/iron_level_locked_chest.png");
    }

    @Override
    public Identifier getAnimationResource(LockedChestLvLIronBlockEntity animatable) {
        return Identifier.of(SA.MOD_ID, "animations/locked_chest.animation.json");
    }
}
