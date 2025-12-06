package net.flyingmylife.seasonal_adventures.gui.handler;

import net.flyingmylife.seasonal_adventures.gui.SAScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class DuckerScreenHandler extends ScreenHandler {
    private final ArrayPropertyDelegate delegate = new ArrayPropertyDelegate(3);
    private BlockPos pos = null;

    public DuckerScreenHandler(int syncId, PlayerInventory ignore) {
        super(SAScreenHandlers.DUCKER_SCREEN_HANDLER, syncId);
        addProperties(delegate);
    }

    public DuckerScreenHandler(int syncId, PlayerInventory ignore, BlockPos pos) {
        super(SAScreenHandlers.DUCKER_SCREEN_HANDLER, syncId);

        delegate.set(0, pos.getX());
        delegate.set(1, pos.getY());
        delegate.set(2, pos.getZ());
        this.pos = pos;

        addProperties(delegate);

    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    public BlockPos getPos() {
        return new BlockPos(delegate.get(0), delegate.get(1), delegate.get(2));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        if (Objects.isNull(pos)) {
            return true;
        }

        return player.squaredDistanceTo(pos.toCenterPos()) <= 3 * 3;
    }
}
