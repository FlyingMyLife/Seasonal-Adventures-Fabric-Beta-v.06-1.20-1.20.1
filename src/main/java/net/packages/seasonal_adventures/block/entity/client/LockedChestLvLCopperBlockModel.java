package net.packages.seasonal_adventures.block.entity.client;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.packages.lumina_lore.systems.particles.ImplementedParticles;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.loading.json.raw.Bone;
import software.bernie.geckolib.model.GeoModel;

public class LockedChestLvLCopperBlockModel extends GeoModel<LockedChestLvLCopperBlockEntity> {
    @Override
    public void setCustomAnimations(LockedChestLvLCopperBlockEntity animatable, long instanceId, AnimationState<LockedChestLvLCopperBlockEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        CoreGeoBone locator1 = this.getAnimationProcessor().getBone("particle1");

        if (locator1 != null) {
            double x = locator1.getPosX();
            double y = locator1.getPosY();
            double z = locator1.getPosZ();

            Vec3d locatorPos = new Vec3d(animatable.getPos().getX() + x, animatable.getPos().getY() + y, animatable.getPos().getZ() + z);

            Vec3d direction = locatorPos.subtract(animatable.getPos().toCenterPos()).normalize();

            double velocityX = direction.getX() * 0.1;
            double velocityY = direction.getY() * 0.1;
            double velocityZ = direction.getZ() * 0.1;

            if (animatable.getWorld() instanceof ServerWorld world) {
                world.addParticle(
                        ImplementedParticles.YELLOW_HIGHRES_PARTICLE,
                        animatable.getPos().getX(),
                        animatable.getPos().getY(),
                        animatable.getPos().getZ(),
                        velocityX,
                        velocityY,
                        velocityZ
                );
            }
        }
    }

    @Override
    public Identifier getModelResource(LockedChestLvLCopperBlockEntity animatable) {
    return new Identifier(SeasonalAdventures.MOD_ID, "geo/locked_chest.geo.json");
    }

    @Override
    public Identifier getTextureResource(LockedChestLvLCopperBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "textures/block/copper_level_locked_chest.png");
    }

    @Override
    public Identifier getAnimationResource(LockedChestLvLCopperBlockEntity animatable) {
        return new Identifier(SeasonalAdventures.MOD_ID, "animations/locked_chest.animation.json");
    }
}
