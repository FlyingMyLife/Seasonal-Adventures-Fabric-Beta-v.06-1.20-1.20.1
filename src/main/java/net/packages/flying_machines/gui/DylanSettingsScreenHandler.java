package net.packages.flying_machines.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.packages.flying_machines.flying_machines;

public class DylanSettingsScreenHandler extends ScreenHandler {
    public DylanSettingsScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(flying_machines.DYLAN_SETTINGS_SCREEN_HANDLER, syncId);
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
