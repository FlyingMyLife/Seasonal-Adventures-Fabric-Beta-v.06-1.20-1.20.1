package net.flyingmylife.seasonal_adventures.gui.handler;

import net.flyingmylife.seasonal_adventures.gui.property_delegate.UnlockingPropertyDelegate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.flyingmylife.seasonal_adventures.gui.SAScreenHandlers;

public class UnlockingScreenHandler extends ScreenHandler {
    private UnlockingPropertyDelegate delegate;
    private final PlayerInventory inventory;
    public UnlockingScreenHandler(int syncId, PlayerInventory inventory) {
        super(SAScreenHandlers.UNLOCKING_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
    }

    public UnlockingScreenHandler(int syncId, PlayerInventory inventory, UnlockingPropertyDelegate delegate) {
        this (syncId, inventory);
        addProperties(delegate);
    }

    public UnlockingPropertyDelegate getDelegate() {
        return delegate;
    }

    public PlayerInventory inventory() {
        return inventory;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
