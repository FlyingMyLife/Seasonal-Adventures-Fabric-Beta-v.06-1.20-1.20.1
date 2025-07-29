package net.flyingmylife.seasonal_adventures.block.entity.lockedChests;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.flyingmylife.seasonal_adventures.block.entity.SABlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.RenderUtil;

public class CopperLCBlockEntity extends LockedChestBlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public CopperLCBlockEntity(BlockPos pos, BlockState state) {
        super(SABlockEntities.LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<CopperLCBlockEntity> animatedBlockEntityAnimationState) {
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

    @Override
    protected int lockLevel() {
        return 0;
    }

}
