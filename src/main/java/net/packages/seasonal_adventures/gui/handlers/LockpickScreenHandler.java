package net.packages.seasonal_adventures.gui.handlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class LockpickScreenHandler extends ScreenHandler {

    public LockpickScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(SeasonalAdventures.LOCKPICK_SCREEN_HANDLER, syncId);
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
