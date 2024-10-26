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
import net.packages.seasonal_adventures.block.entity.LockedChestLvLCopperBlockEntity;

import java.util.HashMap;
import java.util.Map;

public class LockItem extends Item {
    public LockItem(Settings settings, int lockLevel) {
        super(settings);
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
                if (blockEntity instanceof ChestBlockEntity) {
                    ChestBlockEntity chest = (ChestBlockEntity) blockEntity;
                    Map<Integer, ItemStack> inventory = new HashMap<>();
                    for (int i = 0; i < chest.size(); i++) {
                        inventory.put(i, chest.getStack(i).copy());
                    }
                    SeasonalAdventures.sendDebugMessage("Chest contains:" + inventory, player);

                    for (int i = 0; i < ((ChestBlockEntity) blockEntity).size(); i++) {
                        ItemStack item = ((ChestBlockEntity) blockEntity).getStack(i);
                        if (!item.isEmpty()) {
                            ((ChestBlockEntity) blockEntity).removeStack(i);
                        }
                    }
                    world.removeBlock(pos, false);

                    Direction facing = blockState.get(Properties.HORIZONTAL_FACING);
                    BlockState customBlockState = Blocks.LOCKED_CHEST_LVL_COPPER.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);
                    world.setBlockState(pos, customBlockState);

                    BlockEntity lockedChestBlockEntity = world.getBlockEntity(pos);
                    if (lockedChestBlockEntity instanceof LockedChestLvLCopperBlockEntity) {
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
