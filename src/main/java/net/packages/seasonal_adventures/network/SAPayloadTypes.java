package net.packages.seasonal_adventures.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.network.s2c.ConfigSyncPacket;
import net.packages.seasonal_adventures.network.s2c.SecretKeyUpdatePacket;

public class SAPayloadTypes {
    public static class S2C {

        public record SecretKeyUpdatePayload(String key) implements CustomPayload {
            public static final CustomPayload.Id<SecretKeyUpdatePayload> ID = new CustomPayload.Id<>(Identifier.of(SeasonalAdventures.MOD_ID, "secret-key_update_packet"));
            public static final PacketCodec<RegistryByteBuf, SecretKeyUpdatePayload> CODEC = PacketCodec.tuple(
                    PacketCodecs.STRING, SecretKeyUpdatePayload::key,
                    SecretKeyUpdatePayload::new);

            @Override
            public CustomPayload.Id<? extends CustomPayload> getId() {
                return ID;
            }
        }

        public record ConfigSyncPayload(String configData) implements CustomPayload {
            public static final CustomPayload.Id<ConfigSyncPayload> ID = new CustomPayload.Id<>(Identifier.of(SeasonalAdventures.MOD_ID, "client_config_sync_packet"));
            public static final PacketCodec<RegistryByteBuf, ConfigSyncPayload> CODEC = PacketCodec.tuple(
                    PacketCodecs.STRING, ConfigSyncPayload::configData,
                    ConfigSyncPayload::new);

            @Override
            public CustomPayload.Id<? extends CustomPayload> getId() {
                return ID;
            }
        }

        public static void registerPayloadTypes() {
            PayloadTypeRegistry.playS2C().register(SecretKeyUpdatePayload.ID, SecretKeyUpdatePayload.CODEC);
            PayloadTypeRegistry.playS2C().register(ConfigSyncPayload.ID, ConfigSyncPayload.CODEC);
        }
        public static void registerGlobalReceivers() {
            ClientPlayNetworking.registerGlobalReceiver(SecretKeyUpdatePayload.ID, SecretKeyUpdatePacket::register);
            ClientPlayNetworking.registerGlobalReceiver(ConfigSyncPayload.ID, ConfigSyncPacket::registerS2C);
        }
    }
    public static class C2S {

        public static void registerPayloadTypes() {

        }
        public static void registerGlobalReceivers() {
            ServerPlayNetworking.registerGlobalReceiver()
        }
    }
}
