package dev.flyingmylife.seasonal_adventures.network.packet.c2s;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.chunk.ChunkStatus;

import java.util.Objects;

import static dev.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes.C2S.LoadChunkPayload;

public class LoadChunkPacket {

    public static void loadChunkInDimensionOfDreams(Identifier worldLevelKey, int chunkX, int chunkZ) {
        ClientPlayNetworking.send(new LoadChunkPayload(worldLevelKey, chunkX, chunkZ));
    }

    public static void register(LoadChunkPayload payload, ServerPlayNetworking.Context context) {

        ServerWorld world = Objects.requireNonNullElse(context.server().getWorld(RegistryKey.of(RegistryKeys.WORLD, payload.woldLevelKey())),context.server().getWorld(ServerWorld.OVERWORLD));
        world.getChunkManager().getChunk(payload.chunkX(), payload.chunkZ(), ChunkStatus.FULL, true);

    }
}
