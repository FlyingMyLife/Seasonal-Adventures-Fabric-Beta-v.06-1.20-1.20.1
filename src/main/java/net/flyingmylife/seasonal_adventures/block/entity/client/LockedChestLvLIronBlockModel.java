package net.flyingmylife.seasonal_adventures.block.entity.client;

import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.IronLCBlockEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class LockedChestLvLIronBlockModel extends GeoModel<IronLCBlockEntity> {

    @Override
    public Identifier getModelResource(IronLCBlockEntity lockedChestLvLIronBlockEntity) {
        return Identifier.of(SA.MOD_ID, "geo/locked_chest.geo.json");
    }

    @Override
    public Identifier getTextureResource(IronLCBlockEntity lockedChestLvLIronBlockEntity) {
        return Identifier.of(SA.MOD_ID, "textures/block/iron_level_locked_chest.png");
    }

    @Override
    public Identifier getAnimationResource(IronLCBlockEntity animatable) {
        return Identifier.of(SA.MOD_ID, "animations/locked_chest.animation.json");
    }
}
