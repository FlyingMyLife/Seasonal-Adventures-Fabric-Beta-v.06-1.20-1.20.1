package dev.flyingmylife.seasonal_adventures.block.entity.lockedChests;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class LockedChestBlockEntity extends BlockEntity {
    private final Map<Integer, ItemStack> savedInventory = new HashMap<>();
    private final PinAngles pinAngles;

    public LockedChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.pinAngles = new PinAngles(getLockLevel());
    }

    protected abstract int lockLevel();

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        saveInventoryToNbt(nbt, registries);
        nbt.put("pin_angles", pinAngles.getNbt());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        loadInventoryFromNbt(nbt, registries);
        if (nbt.contains("pin_angles")) {
            pinAngles.readNbt(nbt.getCompound("pin_angles"));
        } else {
            pinAngles.generate();
            markDirty();
        }
    }

    public int getLockLevel() {
        return lockLevel();
    }

    public Map<Integer, ItemStack> getSavedInventory() {
        return new HashMap<>(savedInventory);
    }

    public PinAngles getPinAngles() {
        return pinAngles;
    }

    public void saveInventory(Map<Integer, ItemStack> inventory) {
        savedInventory.clear();
        savedInventory.putAll(inventory);
    }

    private void saveInventoryToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        NbtList itemList = new NbtList();
        for (Map.Entry<Integer, ItemStack> entry : savedInventory.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                NbtCompound itemTag = new NbtCompound();
                itemTag.putInt("slot", entry.getKey());
                entry.getValue().toNbt(wrapperLookup, itemTag);
                itemList.add(itemTag);
            }
        }
        nbt.put("saved_inventory", itemList);
    }

    private void loadInventoryFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        savedInventory.clear();
        if (nbt.contains("saved_inventory", 9)) {
            NbtList itemList = nbt.getList("saved_inventory", 10);
            for (int i = 0; i < itemList.size(); i++) {
                NbtCompound itemTag = itemList.getCompound(i);
                int slot = itemTag.getInt("slot");
                Optional<ItemStack> stack = ItemStack.fromNbt(wrapperLookup, itemTag);
                stack.ifPresent(itemStack -> savedInventory.put(slot, itemStack));
            }
        }
    }
}
