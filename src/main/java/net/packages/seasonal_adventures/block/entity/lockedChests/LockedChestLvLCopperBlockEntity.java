package net.packages.seasonal_adventures.block.entity.lockedChests;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.packages.seasonal_adventures.block.entity.SABlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.RenderUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LockedChestLvLCopperBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final int lockLevel = 0;
    private final Map<Integer, ItemStack> savedInventory = new HashMap<>();
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public LockedChestLvLCopperBlockEntity(BlockPos pos, BlockState state) {
        super(SABlockEntities.LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY, pos, state);
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
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        loadInventoryFromNbt(nbt, registries);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        saveInventoryToNbt(nbt, registries);
    }

    public Map<Integer, ItemStack> getSavedInventory() {
        return new HashMap<>(savedInventory);
    }

    public void saveInventory(Map<Integer, ItemStack> inventory) {
        savedInventory.clear();
        savedInventory.putAll(inventory);
    }

    public void saveInventoryToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        NbtList itemList = new NbtList();
        for (Map.Entry<Integer, ItemStack> entry : savedInventory.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                NbtCompound itemTag = new NbtCompound();
                itemTag.putInt("Slot", entry.getKey());
                entry.getValue().toNbt(wrapperLookup, itemTag);
                itemList.add(itemTag);
            }
        }
        nbt.put("SavedInventory", itemList);
    }

    public void loadInventoryFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        savedInventory.clear();
        if (nbt.contains("SavedInventory", 9)) {
            NbtList itemList = nbt.getList("SavedInventory", 10);
            for (int i = 0; i < itemList.size(); i++) {
                NbtCompound itemTag = itemList.getCompound(i);
                int slot = itemTag.getInt("Slot");
                Optional<ItemStack> stack = ItemStack.fromNbt(wrapperLookup, itemTag);
                stack.ifPresent(itemStack -> savedInventory.put(slot, itemStack));
            }
        }
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
