package net.flyingmylife.seasonal_adventures.block.entity.lockedChests;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.flyingmylife.seasonal_adventures.gui.data.UnlockingData;
import net.flyingmylife.seasonal_adventures.gui.handler.UnlockingScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class LockedChestBlockEntity extends BlockEntity {
    private final Map<Integer, ItemStack> savedInventory = new HashMap<>();


    public LockedChestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected abstract int lockLevel();

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        saveInventoryToNbt(nbt, registries);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        loadInventoryFromNbt(nbt, registries);
    }
    public int getLockLevel() {
        return lockLevel();
    }

    public Map<Integer, ItemStack> getSavedInventory() {
        return new HashMap<>(savedInventory);
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
                entry.getValue().encode(wrapperLookup, itemTag);
                itemList.add(itemTag);
            }
        }
        nbt.put("savedInventory", itemList);
    }

    private void loadInventoryFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        savedInventory.clear();
        if (nbt.contains("savedInventory", 9)) {
            NbtList itemList = nbt.getList("savedInventory", 10);
            for (int i = 0; i < itemList.size(); i++) {
                NbtCompound itemTag = itemList.getCompound(i);
                int slot = itemTag.getInt("slot");
                Optional<ItemStack> stack = ItemStack.fromNbt(wrapperLookup, itemTag);
                stack.ifPresent(itemStack -> savedInventory.put(slot, itemStack));
            }
        }
    }
}
