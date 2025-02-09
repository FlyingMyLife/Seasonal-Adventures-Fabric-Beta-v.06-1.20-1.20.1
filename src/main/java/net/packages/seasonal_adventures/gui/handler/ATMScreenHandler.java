package net.packages.seasonal_adventures.gui.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.packages.seasonal_adventures.gui.ScreenHandlers;

public class ATMScreenHandler extends ScreenHandler {
    public ATMScreenHandler(int syncId, PlayerInventory inv) {
        super(ScreenHandlers.ATM_SCREEN_HANDLER, syncId);
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