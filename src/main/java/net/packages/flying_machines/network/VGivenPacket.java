package net.packages.flying_machines.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.packages.flying_machines.item.Items;

public class VGivenPacket {
    private static final Identifier ID = new Identifier("flying_machines", "vID");;

    public static void giveV(int v1, int v5, int v10, int v50, int v100, int v500, int v1000, int v10000) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeInt(v1);
        buf.writeInt(v5);
        buf.writeInt(v10);
        buf.writeInt(v50);
        buf.writeInt(v100);
        buf.writeInt(v500);
        buf.writeInt(v1000);
        buf.writeInt(v10000);

        ClientPlayNetworking.send(ID, buf);
    }
    private static void giveItemToPlayer(ServerPlayerEntity player, ItemStack item, int amount) {
        for (;;) {
            amount--;
            ItemStack stack = new ItemStack(item.getItem(), amount);
            player.getInventory().insertStack(stack);
            if (amount <= 0) break;
        }
    }
}