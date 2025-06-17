package dev.flyingmylife.seasonal_adventures.gui.handler;

import dev.flyingmylife.seasonal_adventures.block.entity.lockedChests.PinAngles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import dev.flyingmylife.seasonal_adventures.gui.SAScreenHandlers;

public class LockpickScreenHandler extends ScreenHandler {
    private PinAngles angles;
    private final PlayerInventory inventory;
    public LockpickScreenHandler(int syncId, PlayerInventory inventory) {
        super(SAScreenHandlers.LOCKPICK_SCREEN_HANDLER, syncId);
        this.angles = null;
        this.inventory = inventory;
    }

    public LockpickScreenHandler(int syncId, PlayerInventory inventory, PinAngles angles) {
        this (syncId, inventory);
        this.angles = angles;
    }

    public PinAngles getAngles() {
        return angles;
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
