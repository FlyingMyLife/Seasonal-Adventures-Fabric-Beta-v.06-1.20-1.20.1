package net.packages.seasonal_adventures.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.events.JDBCardHandler;
import net.packages.seasonal_adventures.util.WorldUtils;

public class CardGivenPacket {
    private static final Identifier ID = new Identifier(SeasonalAdventures.MOD_ID, "card_given");
    ;

    public static void GiveCard() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            server.execute( () -> {
                if (player != null) {
                    JDBCardHandler.giveCard(player, server, WorldUtils.getOverworld(server));
                }
            });
        });
    }
}
