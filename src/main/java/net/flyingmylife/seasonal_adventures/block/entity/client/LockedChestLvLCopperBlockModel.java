package net.flyingmylife.seasonal_adventures.block.entity.client;

import net.flyingmylife.seasonal_adventures.SA;
import net.minecraft.util.Identifier;
import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.CopperLCBlockEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class LockedChestLvLCopperBlockModel extends GeoModel<CopperLCBlockEntity> {
    @Override
    public Identifier getModelResource(CopperLCBlockEntity lockedChestLvLCopperBlockEntity) {
        return Identifier.of(SA.MOD_ID, "geo/locked_chest.geo.json");
    }

    @Override
    public Identifier getTextureResource(CopperLCBlockEntity lockedChestLvLCopperBlockEntity) {
        return Identifier.of(SA.MOD_ID, "textures/block/copper_level_locked_chest.png");
    }

    @Override
    public Identifier getAnimationResource(CopperLCBlockEntity animatable) {
        return Identifier.of(SA.MOD_ID, "animations/locked_chest.animation.json");
    }
}
