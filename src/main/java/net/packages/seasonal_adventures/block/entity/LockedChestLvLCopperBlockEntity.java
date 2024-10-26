package net.packages.seasonal_adventures.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.HashMap;
import java.util.Map;

public class LockedChestLvLCopperBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final Map<Integer, ItemStack> savedInventory = new HashMap<>();
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public LockedChestLvLCopperBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<LockedChestLvLCopperBlockEntity> animatedBlockEntityAnimationState) {
        animatedBlockEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        loadInventoryFromNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        saveInventoryToNbt(nbt);
    }

    public Map<Integer, ItemStack> getSavedInventory() {
        return new HashMap<>(savedInventory);
    }

    public void saveInventory(Map<Integer, ItemStack> inventory) {
        savedInventory.clear();
        savedInventory.putAll(inventory);
    }

    public void saveInventoryToNbt(NbtCompound nbt) {
        NbtList itemList = new NbtList();
        for (Map.Entry<Integer, ItemStack> entry : savedInventory.entrySet()) {
            NbtCompound itemTag = new NbtCompound();
            itemTag.putInt("Slot", entry.getKey());
            entry.getValue().writeNbt(itemTag);
            itemList.add(itemTag);
        }
        nbt.put("SavedInventory", itemList);
    }

    private void loadInventoryFromNbt(NbtCompound nbt) {
        savedInventory.clear();
        NbtList itemList = nbt.getList("SavedInventory", 10);
        for (int i = 0; i < itemList.size(); i++) {
            NbtCompound itemTag = itemList.getCompound(i);
            int slot = itemTag.getInt("Slot");
            ItemStack stack = ItemStack.fromNbt(itemTag);
            savedInventory.put(slot, stack);
        }
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
