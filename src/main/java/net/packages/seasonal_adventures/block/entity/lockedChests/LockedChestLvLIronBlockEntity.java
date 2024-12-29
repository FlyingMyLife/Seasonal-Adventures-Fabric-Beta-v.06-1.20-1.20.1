package net.packages.seasonal_adventures.block.entity.lockedChests;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.packages.seasonal_adventures.block.entity.BlockEntities;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LockedChestLvLIronBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final int lockLevel = 1;
    private int [] lockAngles = new int[13];
    private final Map<Integer, ItemStack> savedInventory = new HashMap<>();
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public LockedChestLvLIronBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.LOCKED_CHEST_LVL_IRON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<LockedChestLvLIronBlockEntity> animatedBlockEntityAnimationState) {
        animatedBlockEntityAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        loadInventoryFromNbt(nbt);
        loadLockDegrees(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        saveInventoryToNbt(nbt);
        saveLockDegrees(nbt);
    }
    public int [] getLockAngles() {
        System.out.println("Lock angles: " + lockAngles);
        return lockAngles;
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

    private void saveLockDegrees(NbtCompound nbt) {
        generateLockDegrees();
        NbtList lockList = new NbtList();
        for (int i = 1; i <= 12; i++) {
            lockList.add(NbtInt.of(lockAngles[i]));
        }
        nbt.put("lockAngles", lockList);
    }
    private void loadLockDegrees(NbtCompound nbt) {
        NbtList lockList = nbt.getList("lockAngles", NbtElement.INT_TYPE);
        for (int i = 1; i <= 12; i++) {
            if (i - 1 < lockList.size()) {
                lockAngles[i] = lockList.getInt(i - 1);
            } else {
                lockAngles[i] = 0;
            }
        }
    }

    public void generateLockDegrees() {
        Random random = new Random();
        int[] locks = new int[12];
        int lockCount = 0;

        if (lockLevel <= 2) {
            int minSpacing = 30;

            locks[lockCount++] = generateFirstLock(random);

            int maxLocks = lockLevel == 0 ? 5 : lockLevel == 1 ? 7 : 8;

            while (lockCount < maxLocks) {
                locks[lockCount] = generateNextDegree(locks, lockCount, minSpacing, random);
                lockCount++;
            }

            assignLocks(locks, lockCount);

        } else if (lockLevel == 3) {
            int spacing = 36;
            for (int i = 0; i < 10; i++) {
                locks[i] = (i * spacing) % 360;
            }
            assignLocks(locks, 10);

        } else if (lockLevel == 4) {
            int spacing = 30;
            for (int i = 0; i < 12; i++) {
                locks[i] = (i * spacing) % 360;
            }
            assignLocks(locks, 12);
        }
    }

    private int generateFirstLock(Random random) {
        int firstLock;
        while (true) {
            firstLock = random.nextInt(360);
            if (!isNearProhibitedRange(firstLock)) {
                break;
            }
        }
        return firstLock;
    }

    private static int generateNextDegree(int[] locks, int lockCount, int minSpacing, Random random) {
        int newDegree;
        while (true) {
            newDegree = random.nextInt(360);
            boolean isValid = true;
            for (int i = 0; i < lockCount; i++) {
                int diff = Math.abs(newDegree - locks[i]);
                diff = Math.min(diff, 360 - diff);
                if (diff < minSpacing || isNearProhibitedRange(newDegree)) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                break;
            }
        }
        return newDegree;
    }

    private static boolean isNearProhibitedRange(int degree) {
        return (degree <= 5 || degree >= 355);
    }

    private void assignLocks(int[] locks, int lockCount) {
        if (lockCount > 0) lockAngles[1] = locks[0];
        if (lockCount > 1) lockAngles[2] = locks[1];
        if (lockCount > 2) lockAngles[3] = locks[2];
        if (lockCount > 3) lockAngles[4] = locks[3];
        if (lockCount > 4) lockAngles[5] = locks[4];
        if (lockCount > 5) lockAngles[6] = locks[5];
        if (lockCount > 6) lockAngles[7] = locks[6];
        if (lockCount > 7) lockAngles[8] = locks[7];
        if (lockCount > 8) lockAngles[9] = locks[8];
        if (lockCount > 9) lockAngles[10] = locks[9];
        if (lockCount > 10) lockAngles[11] = locks[10];
        if (lockCount > 11) lockAngles[12] = locks[11];
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
