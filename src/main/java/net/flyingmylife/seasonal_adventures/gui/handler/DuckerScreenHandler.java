package net.flyingmylife.seasonal_adventures.gui.handler;

import net.flyingmylife.seasonal_adventures.gui.SAScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class DuckerScreenHandler extends ScreenHandler {
    private final ScreenHandlerContext context;

    public DuckerScreenHandler(int syncId, PlayerInventory ignore) {
        this(syncId, ignore, ScreenHandlerContext.EMPTY);
    }

    public DuckerScreenHandler(int syncId, PlayerInventory ignore, ScreenHandlerContext context) {
        super(SAScreenHandlers.DUCKER_SCREEN_HANDLER, syncId);
        this.context = context;
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
