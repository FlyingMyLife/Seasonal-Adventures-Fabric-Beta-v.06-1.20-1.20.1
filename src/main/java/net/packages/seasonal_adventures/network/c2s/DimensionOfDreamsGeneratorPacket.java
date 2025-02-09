package net.packages.seasonal_adventures.network.c2s;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.world.TeleportTarget;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.util.game.ServerUtils;
import net.packages.seasonal_adventures.world.data.persistent_state.WorldDataPersistentState;
import net.packages.seasonal_adventures.world.dimension.Dimensions;

public class DimensionOfDreamsGeneratorPacket {
    private static final Identifier ID = Identifier.of(SeasonalAdventures.MOD_ID, "dimension_teleport_packet");
    ;

    public static void teleportToDimensionOfDreams() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerWorld world = server.getWorld(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY);
        assert world != null;

        BlockPos targetPos = new BlockPos(8, 21, 9);

        TeleportTarget target = new TeleportTarget(
                new Vec3d(targetPos.getX() + 0.5f, targetPos.getY(), targetPos.getZ() + 0.5f),
                Vec3d.ZERO,
                player.player().getPitch();
        player.getPitch()
                );
        FabricDimensions.teleport(player, world, target);
        WorldDataPersistentState state = WorldDataPersistentState.getServerState(server);
        if (!state.initializedDimensionOfDreams) {
            ServerUtils.placeStructure(world, Identifier.of("seasonal_adventures:island_of_dreams"), new BlockPos(0, 0, 0));
            for (int x = -8; x <= 24; x++) {
                for (int z = -8; z <= 24; z++) {
                    world.removeBlock(new BlockPos(x, -61, z), false);
                }
            }
            state.initializedDimensionOfDreams = true;
            SeasonalAdventures.LOGGER.info("Generated start island in dimension of dreams");
        }
    }
}