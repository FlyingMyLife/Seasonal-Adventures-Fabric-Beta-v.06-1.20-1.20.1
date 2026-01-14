package net.flyingmylife.seasonal_adventures.network.service;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.gui.data.ducker.ShelterData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ServerDataQueryService {
    public static final DataType<ShelterData> SHELTER_DATA = new DataType<>(ShelterData::get, ShelterData::parse);
    public record Payload(int requestId, String dataType, NbtCompound data) implements CustomPayload {
        public static final CustomPayload.Id<Payload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "data_request"));
        public static final PacketCodec<RegistryByteBuf, Payload> CODEC = PacketCodec.tuple(
                PacketCodecs.INTEGER, Payload::requestId,
                PacketCodecs.STRING, Payload::dataType,
                PacketCodecs.NBT_COMPOUND, Payload::data,
                Payload::new
        );

        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    public static void registerDataTypes() {
        DataType.register(Identifier.of(SA.MOD_ID, "shelter_data"), SHELTER_DATA);
    }

    public static void registerClientGlobalReceiver(Payload payload, ClientPlayNetworking.Context ignore) {
        Manager.received(payload.requestId, payload.data);
    }

    public static void registerServerGlobalReceiver(Payload payload, ServerPlayNetworking.Context context) {
        int requestId = payload.requestId;

        Identifier typeId = Identifier.of(payload.dataType);
        DataType<?> type = DataType.get(typeId);

        NbtCompound result = type.get.apply(new Context(context.server(), context.player(), payload.data));

        Payload response = new Payload(requestId, payload.dataType, result);
        ServerPlayNetworking.send(context.player(), response);
    }

    public static class Manager {
        private static final Map<Integer, Entry<?>> awaiting = new HashMap<>();

        public static <T> CompletableFuture<T> requestData(Identifier typeId, NbtCompound requestArgs) {
            int requestId = new Random().nextInt(0, (int) 10e6);

            CompletableFuture<T> future = new CompletableFuture<>();
            awaiting.put(requestId, new Entry<>(requestId, typeId, future));

            ClientPlayNetworking.send(new Payload(requestId, typeId.toString(), requestArgs));

            return future;
        }

        @SuppressWarnings("unchecked")
        public static void received(int requestId, NbtCompound data) {
            Entry<?> entry = awaiting.remove(requestId);
            if (entry == null) return;

            DataType<?> type = DataType.get(entry.typeId());
            if (type == null) {
                entry.future().completeExceptionally(
                        new IllegalStateException("Unknown DataType: " + entry.typeId())
                );
                return;
            }

            Object parsed = type.parse().apply(data);
            ((CompletableFuture<Object>) entry.future()).complete(parsed);
        }

        private record Entry<T>(int requestId, Identifier typeId, CompletableFuture<T> future) {}
    }

    public record DataType<T>(Function<Context, NbtCompound> get, Function<NbtCompound, T> parse) {
        private static final HashMap<Identifier, DataType<?>> TYPES = new HashMap<>();
        
        public static void register(Identifier id, DataType<?> dataType) {
            TYPES.put(id, dataType);
        }
        
        public static DataType<?> get(Identifier id) {
            return TYPES.get(id);
        }
    }
    public record Context(MinecraftServer server, ServerPlayerEntity player, NbtCompound requestData) {}
}
