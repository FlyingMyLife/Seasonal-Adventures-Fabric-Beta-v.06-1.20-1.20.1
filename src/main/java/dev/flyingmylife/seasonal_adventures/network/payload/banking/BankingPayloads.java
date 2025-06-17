package dev.flyingmylife.seasonal_adventures.network.payload.banking;

import dev.flyingmylife.seasonal_adventures.SA;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class BankingPayloads {
    public record BasicOperationPayload(String typeId, int amount) implements CustomPayload {
        public static final CustomPayload.Id<BasicOperationPayload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "basic_operation_payload"));
        public static final PacketCodec<RegistryByteBuf, BasicOperationPayload> CODEC = PacketCodec.tuple(
                PacketCodecs.STRING, BasicOperationPayload::typeId,
                PacketCodecs.INTEGER, BasicOperationPayload::amount,
                BasicOperationPayload::new
        );

        @Override
        public CustomPayload.Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    public record RequestCardOperationPayload() implements CustomPayload {
        public static final CustomPayload.Id<RequestCardOperationPayload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "request_card_payload"));
        public static final PacketCodec<RegistryByteBuf, RequestCardOperationPayload> CODEC = PacketCodec.unit(new RequestCardOperationPayload());

        @Override
        public CustomPayload.Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    public record WarningOperationPayload(String ownerUUID) implements CustomPayload {
        public static final CustomPayload.Id<WarningOperationPayload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "send_warning_payload"));
        public static final PacketCodec<RegistryByteBuf, WarningOperationPayload> CODEC = PacketCodec.tuple(
                PacketCodecs.STRING, WarningOperationPayload::ownerUUID,
                WarningOperationPayload::new
        );

        @Override
        public CustomPayload.Id<? extends CustomPayload> getId() {
            return ID;
        }
    }

    public record FineOperationPayload(int fineAmount, String reason) implements CustomPayload {
        public static final CustomPayload.Id<FineOperationPayload> ID = new CustomPayload.Id<>(Identifier.of(SA.MOD_ID, "fine_payload"));
        public static final PacketCodec<RegistryByteBuf, FineOperationPayload> CODEC = PacketCodec.tuple(
                PacketCodecs.INTEGER, FineOperationPayload::fineAmount,
                PacketCodecs.STRING, FineOperationPayload::reason,
                FineOperationPayload::new
        );

        @Override
        public CustomPayload.Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
}
