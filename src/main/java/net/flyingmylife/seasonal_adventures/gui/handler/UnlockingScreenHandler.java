package net.flyingmylife.seasonal_adventures.gui.handler;

import net.flyingmylife.seasonal_adventures.block.entity.lockedChests.LockedChestBlockEntity;
import net.flyingmylife.seasonal_adventures.gui.data.UnlockingData;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.flyingmylife.seasonal_adventures.gui.SAScreenHandlers;
import net.minecraft.screen.ScreenHandlerContext;

public class UnlockingScreenHandler extends ScreenHandler {
    private final PlayerInventory inventory;
    private final ArrayPropertyDelegate delegate;

    public UnlockingScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, 0);
    }


    public UnlockingScreenHandler(int syncId, PlayerInventory inventory, int lockLevel) {
        super(SAScreenHandlers.UNLOCKING_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        ArrayPropertyDelegate delegate = new ArrayPropertyDelegate(1);
        delegate.set(0, lockLevel);
        addProperties(delegate);
        this.delegate = delegate;
    }

    public int getLockLevel() {
        return delegate.get(0);
    }

    public PlayerInventory getInventory() {
        return inventory;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }
}