package net.packages.seasonal_adventures.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class BankingOperationsPacket {

    public static Identifier ID = new Identifier(SeasonalAdventures.MOD_ID, "banking_operations");
    public void sendPacket(OperationType type, Integer...hello) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeEnumConstant(type);
        ClientPlayNetworking.send(ID, buf);
    }
    public void register(){
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            OperationType type = buf.readEnumConstant(OperationType.class);
            server.execute( () -> {
                if (player != null) {
                }
            });
        });
    }
}
