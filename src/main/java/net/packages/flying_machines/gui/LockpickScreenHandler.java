package net.packages.flying_machines.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.packages.flying_machines.flying_machines;

public class LockpickScreenHandler extends ScreenHandler {

    public LockpickScreenHandler(int i, PlayerInventory playerInventory) {
        super(flying_machines.LOCKPICK_SCREEN_HANDLER, 0);
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
