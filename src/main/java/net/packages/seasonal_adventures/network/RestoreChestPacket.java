package net.packages.seasonal_adventures.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.entity.lockedChests.LockedChestLvLCopperBlockEntity;

import java.util.Map;

public class RestoreChestPacket {
    public static final Identifier ID = new Identifier(SeasonalAdventures.MOD_ID, "restore_chest");

    public static void sendRestoreChestRequest(BlockPos pos, int lockLevel) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeBlockPos(pos);
        buf.writeInt(lockLevel);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            int lockLevel = buf.readInt();

            server.execute(() -> {
                World world = player.getWorld();

                BlockEntity lockedChestBlockEntity = world.getBlockEntity(pos);

                if (lockedChestBlockEntity instanceof LockedChestLvLCopperBlockEntity lockedChest) {
                    NbtCompound chestNbt = new NbtCompound();
                    lockedChest.saveInventoryToNbt(chestNbt);

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

                    //Зачем ты на это смотришь ?

                }
            });
        });
    }
}