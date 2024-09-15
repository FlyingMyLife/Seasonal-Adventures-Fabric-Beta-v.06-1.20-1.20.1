package net.packages.flying_machines.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.packages.flying_machines.events.PlayerCardHandler;
import net.packages.flying_machines.util.WorldUtils;

public class CardGivenPacket {
    private static final Identifier ID = new Identifier("flying_machines", "card_given_update");
    ;

    public static void GiveCard() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (player != null) {
                    PlayerCardHandler.giveCard(player, server, WorldUtils.getOverworld(server));
                }
            });
        });
    }
}
