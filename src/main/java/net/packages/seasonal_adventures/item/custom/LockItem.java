package net.packages.seasonal_adventures.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.Blocks;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLIronBlockEntity;

import java.util.HashMap;
import java.util.Map;

public class LockItem extends Item {
    private int lockLevel;
    public LockItem(Settings settings, int lockLevel) {
        super(settings);
        this.lockLevel = lockLevel;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();

        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof ChestBlock) {
            if (!world.isClient) {

                BlockEntity blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof ChestBlockEntity chest) {
                    Map<Integer, ItemStack> inventory = new HashMap<>();
                    for (int i = 0; i < chest.size(); i++) {
                        inventory.put(i, chest.getStack(i).copy());
                    }
                    assert player != null;

                    for (int i = 0; i < ((ChestBlockEntity) blockEntity).size(); i++) {
                        ItemStack item = ((ChestBlockEntity) blockEntity).getStack(i);
                        if (!item.isEmpty()) {
                            ((ChestBlockEntity) blockEntity).removeStack(i);
                        }
                    }
                    world.removeBlock(pos, false);

                    Direction facing = blockState.get(Properties.HORIZONTAL_FACING);
                    BlockState lockedChestBlockState = null;
                    if (lockLevel == 0) lockedChestBlockState = Blocks.LOCKED_CHEST_LVL_COPPER.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);
                    if (lockLevel == 1) lockedChestBlockState = Blocks.LOCKED_CHEST_LVL_IRON.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);
                    world.setBlockState(pos, lockedChestBlockState);

                    BlockEntity lockedChestBlockEntity = world.getBlockEntity(pos);
                    if (lockedChestBlockEntity instanceof LockedChestLvLCopperBlockEntity && lockLevel == 0) {
                        ((LockedChestLvLCopperBlockEntity) lockedChestBlockEntity).saveInventory(inventory);
                    }
                    if (lockedChestBlockEntity instanceof LockedChestLvLIronBlockEntity && lockLevel == 1) {
                        ((LockedChestLvLIronBlockEntity) lockedChestBlockEntity).saveInventory(inventory);
                    }
                    if (lockedChestBlockEntity instanceof LockedChestLvLCopperBlockEntity && lockLevel == 2) {
                        ((LockedChestLvLCopperBlockEntity) lockedChestBlockEntity).saveInventory(inventory);
                    }
                    if (lockedChestBlockEntity instanceof LockedChestLvLCopperBlockEntity && lockLevel == 3) {
                        ((LockedChestLvLCopperBlockEntity) lockedChestBlockEntity).saveInventory(inventory);
                    }
                    if (lockedChestBlockEntity instanceof LockedChestLvLCopperBlockEntity && lockLevel == 4) {
                        ((LockedChestLvLCopperBlockEntity) lockedChestBlockEntity).saveInventory(inventory);
                    }
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.UNCOMMON;
    }
}
