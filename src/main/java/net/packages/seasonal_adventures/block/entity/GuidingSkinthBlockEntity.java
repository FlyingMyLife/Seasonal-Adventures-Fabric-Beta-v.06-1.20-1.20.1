package net.packages.seasonal_adventures.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.RenderUtil;

public class GuidingSkinthBlockEntity extends BlockEntity implements GeoBlockEntity {
    public GuidingSkinthBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.GUIDING_SKINTH_BLOCK_ENTITY,pos, state);
    }
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }
    private PlayState predicate(AnimationState<GuidingSkinthBlockEntity> animatedBlockEntityAnimationState) {
        animatedBlockEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    @Override
    public double getTick(Object blockEntity) {
        return RenderUtil.getCurrentTick();
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
