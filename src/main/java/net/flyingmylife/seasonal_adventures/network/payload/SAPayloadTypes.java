package net.flyingmylife.seasonal_adventures.network.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.flyingmylife.seasonal_adventures.network.packet.c2s.*;
import net.flyingmylife.seasonal_adventures.network.packet.s2c.InitializeClientDataPacket;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.flyingmylife.seasonal_adventures.SA;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static net.flyingmylife.seasonal_adventures.network.payload.banking.BankingPayloads.*;

public class SAPayloadTypes {
    public static class S2C {
        public record InitializeClientDataPayload(NbtCompound nbt) implements CustomPayload {
            public static final CustomPayload.Id<InitializeClientDataPayload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "initialize_client_data_payload"));
            public static final PacketCodec<RegistryByteBuf, InitializeClientDataPayload> CODEC = PacketCodec.tuple(
                    PacketCodecs.NBT_COMPOUND, InitializeClientDataPayload::nbt,
                    InitializeClientDataPayload::new
            );

            @Override
            public Id<? extends CustomPayload> getId() {
                return ID;
            }
            public Optional<Long> getHashedSeed() {
                if (nbt.contains("hashed_seed")) {
                    return Optional.of(nbt.getLong("hashed_seed"));
                } else {
                    return Optional.empty();
                }
            }
            public static InitializeClientDataPayload create(long serverSeed) {
                NbtCompound nbt = new NbtCompound();
                MessageDigest digest = null;
                try {
                    digest = MessageDigest.getInstance("SHA-256");
                    byte[] hash = digest.digest(Long.toString(serverSeed).getBytes());
                    ByteBuffer buffer = ByteBuffer.wrap(hash);
                    nbt.putLong("hashed_seed", Math.abs(buffer.getLong()));

                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                return new InitializeClientDataPayload(nbt);
            }
        }
        public static void registerPayloadTypes() {
            PayloadTypeRegistry.playS2C().register(InitializeClientDataPayload.ID, InitializeClientDataPayload.CODEC);
        }
        public static void registerGlobalReceivers() {
            ClientPlayNetworking.registerGlobalReceiver(InitializeClientDataPayload.ID, InitializeClientDataPacket::register);
        }
    }
    public static class C2S {

        public record DODGeneratorPayload() implements CustomPayload {
            public static final CustomPayload.Id<DODGeneratorPayload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "dimension_of_dreams_generator_payload"));
            public static final PacketCodec<RegistryByteBuf, DODGeneratorPayload> CODEC = PacketCodec.unit(new DODGeneratorPayload());

            @Override
            public CustomPayload.Id<? extends CustomPayload> getId() {
                return ID;
            }
        }
        public record RestoreChestPayload(BlockPos pos) implements CustomPayload {
            public static final CustomPayload.Id<RestoreChestPayload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "restore_chest_payload"));
            public static final PacketCodec<RegistryByteBuf, RestoreChestPayload> CODEC = PacketCodec.tuple(
                    BlockPos.PACKET_CODEC, RestoreChestPayload::pos,
                    RestoreChestPayload::new);

            @Override
            public Id<? extends CustomPayload> getId() {
                return ID;
            }
        }
        public record LoadChunkPayload(Identifier woldLevelKey, int chunkX, int chunkZ) implements CustomPayload {
            public static final CustomPayload.Id<LoadChunkPayload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "load_chunk_payload"));
            public static final PacketCodec<RegistryByteBuf, LoadChunkPayload> CODEC = PacketCodec.tuple(
                    Identifier.PACKET_CODEC, LoadChunkPayload::woldLevelKey,
                    PacketCodecs.INTEGER, LoadChunkPayload::chunkX,
                    PacketCodecs.INTEGER, LoadChunkPayload::chunkZ,
                    LoadChunkPayload::new);
            @Override
            public Id<? extends CustomPayload> getId() {
                return ID;
            }
        }
        public record InsertItemStackPayload(Identifier itemId, int amount) implements CustomPayload {
            public static final CustomPayload.Id<InsertItemStackPayload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "insert_item_payload"));
            public static final PacketCodec<RegistryByteBuf, InsertItemStackPayload> CODEC = PacketCodec.tuple(
                    Identifier.PACKET_CODEC, InsertItemStackPayload::itemId,
                    PacketCodecs.INTEGER, InsertItemStackPayload::amount,
                    InsertItemStackPayload::new);
            @Override
            public Id<? extends CustomPayload> getId() {
                return ID;
            }
        }
        public record RemoveItemPayload(Identifier itemId, int amount) implements CustomPayload {
            public static final CustomPayload.Id<RemoveItemPayload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "remove_item_payload"));
            public static final PacketCodec<RegistryByteBuf, RemoveItemPayload> CODEC = PacketCodec.tuple(
                    Identifier.PACKET_CODEC, RemoveItemPayload::itemId,
                    PacketCodecs.INTEGER, RemoveItemPayload::amount,
                    RemoveItemPayload::new);
            @Override
            public Id<? extends CustomPayload> getId() {
                return ID;
            }
        }
        public static void registerPayloadTypes() {
            PayloadTypeRegistry.playC2S().register(BasicOperationPayload.ID, BasicOperationPayload.CODEC);
            PayloadTypeRegistry.playC2S().register(RequestCardOperationPayload.ID, RequestCardOperationPayload.CODEC);
            PayloadTypeRegistry.playC2S().register(WarningOperationPayload.ID, WarningOperationPayload.CODEC);
            PayloadTypeRegistry.playC2S().register(FineOperationPayload.ID, FineOperationPayload.CODEC);

            PayloadTypeRegistry.playC2S().register(DODGeneratorPayload.ID, DODGeneratorPayload.CODEC);
            PayloadTypeRegistry.playC2S().register(RestoreChestPayload.ID, RestoreChestPayload.CODEC);
            PayloadTypeRegistry.playC2S().register(LoadChunkPayload.ID, LoadChunkPayload.CODEC);
            PayloadTypeRegistry.playC2S().register(InsertItemStackPayload.ID, InsertItemStackPayload.CODEC);
            PayloadTypeRegistry.playC2S().register(RemoveItemPayload.ID, RemoveItemPayload.CODEC);
        }
        public static void registerGlobalReceivers () {
            ServerPlayNetworking.registerGlobalReceiver(BasicOperationPayload.ID, BankingOperationsPacket::registerBasicOperation);
            ServerPlayNetworking.registerGlobalReceiver(RequestCardOperationPayload.ID, BankingOperationsPacket::registerRequestCardOperation);
            ServerPlayNetworking.registerGlobalReceiver(WarningOperationPayload.ID, BankingOperationsPacket::registerWarningOperation);
            ServerPlayNetworking.registerGlobalReceiver(FineOperationPayload.ID, BankingOperationsPacket::registerFineOperation);

            ServerPlayNetworking.registerGlobalReceiver(DODGeneratorPayload.ID, TransportToDODPacket::register);
            ServerPlayNetworking.registerGlobalReceiver(RestoreChestPayload.ID, RestoreChestPacket::register);
            ServerPlayNetworking.registerGlobalReceiver(LoadChunkPayload.ID, LoadChunkPacket::register);
            ServerPlayNetworking.registerGlobalReceiver(InsertItemStackPayload.ID, InsertItemStackPacket::register);
            ServerPlayNetworking.registerGlobalReceiver(RemoveItemPayload.ID, RemoveItemPacket::register);
        }
    }
}
