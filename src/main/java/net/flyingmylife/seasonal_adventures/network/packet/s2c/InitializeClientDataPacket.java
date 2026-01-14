package net.flyingmylife.seasonal_adventures.network.packet.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes;
import net.flyingmylife.seasonal_adventures.world.data.ClientData;

public class InitializeClientDataPacket {
    public static void register(SAPayloadTypes.S2C.InitializeClientDataPayload payload, ClientPlayNetworking.Context context) {
        ClientData.setSeed(payload.getHashedSeed().orElse(-1L));
    }
}
