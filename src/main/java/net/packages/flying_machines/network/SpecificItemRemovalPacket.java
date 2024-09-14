package net.packages.flying_machines.network;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class SpecificItemRemovalPacket {
    public static final Identifier ID = new Identifier("flying_machines", "specific_item_removal");

    public static void sendItemRemovalRequest(Item itemToRemove, int countToRemove) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIdentifier(Registries.ITEM.getId(itemToRemove));
        buf.writeInt(countToRemove);

        ClientPlayNetworking.send(ID, buf);
    }

    public static void removeItemStack(PlayerEntity player, Item itemToRemove, int countToRemove) {
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);

            if (stack.getItem().equals(itemToRemove)) {
                int stackCount = stack.getCount();

                if (stackCount > countToRemove) {
                    stack.decrement(countToRemove);
                    break;
                } else {
                    countToRemove -= stackCount;
                    inventory.removeStack(i);
                    if (countToRemove <= 0) {
                        break;
                    }
                }
            }
        }
    }
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            Identifier itemId = buf.readIdentifier();
            int countToRemove = buf.readInt();

            server.execute(() -> {
                Item itemToRemove = Registries.ITEM.get(itemId);
                removeItemStack(player, itemToRemove, countToRemove);
            });
        });
    }
}
