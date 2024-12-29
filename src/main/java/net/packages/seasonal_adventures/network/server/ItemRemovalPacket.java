package net.packages.seasonal_adventures.network.server;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class ItemRemovalPacket {
    public static final Identifier ID = new Identifier(SeasonalAdventures.MOD_ID, "item_removal");

    public static void ItemStackRemove() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ItemStack mainHandStack = player.getInventory().getMainHandStack();

                if (!mainHandStack.isEmpty()) {

                    mainHandStack.decrement(1);

                    player.getInventory().markDirty();
                }
            });
        });
    }

}
