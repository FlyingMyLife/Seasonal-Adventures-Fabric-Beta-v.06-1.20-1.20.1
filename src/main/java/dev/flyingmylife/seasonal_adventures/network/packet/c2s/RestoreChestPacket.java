package dev.flyingmylife.seasonal_adventures.network.packet.c2s;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import dev.flyingmylife.seasonal_adventures.block.entity.lockedChests.LockedChestBlockEntity;

import java.util.Map;

import static dev.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes.C2S.RestoreChestPayload;

public class RestoreChestPacket {

    public static void restoreChest(BlockPos pos) {
        ClientPlayNetworking.send(new RestoreChestPayload(pos));
    }

    public static void register(RestoreChestPayload payload, ServerPlayNetworking.Context context) {
        BlockPos pos = payload.pos();
        ServerWorld world = context.player().getServerWorld();
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof LockedChestBlockEntity lockedChest) {
            BlockState blockState = world.getBlockState(pos);
            world.removeBlock(pos, false);

            Direction facing = blockState.get(Properties.HORIZONTAL_FACING);
            BlockState chestBlockState = Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, facing);

            world.setBlockState(pos, chestBlockState, Block.NOTIFY_ALL);
            world.updateListeners(pos, chestBlockState, chestBlockState, Block.NOTIFY_ALL);

            BlockEntity chestEntity = world.getBlockEntity(pos);

            if (chestEntity instanceof ChestBlockEntity chestBlockEntity) {
                for (Map.Entry<Integer, ItemStack> entry : lockedChest.getSavedInventory().entrySet()) {
                    chestBlockEntity.setStack(entry.getKey(), entry.getValue());
                }
                chestBlockEntity.markDirty();
            }
        }
    }
}