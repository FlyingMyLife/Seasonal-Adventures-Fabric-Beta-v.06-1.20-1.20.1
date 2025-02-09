package net.packages.seasonal_adventures.network.server;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.chunk.ChunkStatus;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.world.dimension.Dimensions;

public class LoadChunkPacket {
    public static final Identifier ID = Identifier.of(SeasonalAdventures.MOD_ID, "load_chunk_packet");
    public static void loadChunkInDimensionOfDreams(int chunkX, int chunkZ) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(chunkX);
        buf.writeInt(chunkZ);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            int chunkX = buf.readInt();
            int chunkZ = buf.readInt();
            server.execute( () -> {
                ServerWorld world = server.getWorld(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY);
                world.getChunkManager().getChunk(chunkX, chunkZ, ChunkStatus.FULL, true);
            });
        });
    }
}
