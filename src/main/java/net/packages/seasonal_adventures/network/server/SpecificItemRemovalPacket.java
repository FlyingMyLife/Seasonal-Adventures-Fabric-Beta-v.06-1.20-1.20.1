package net.packages.seasonal_adventures.network.server;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class SpecificItemRemovalPacket {
    public static final Identifier ID = new Identifier(SeasonalAdventures.MOD_ID, "specific_item_removal_packet");

    public static void sendItemRemovalRequest(Item itemToRemove, int countToRemove) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIdentifier(Registries.ITEM.getId(itemToRemove));
        buf.writeInt(countToRemove);

        ClientPlayNetworking.send(ID, buf);
    }
    public static void sendItemStackRemovalRequest(ItemStack itemStack, int countToRemove) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIdentifier(Registries.ITEM.getId(itemStack.getItem()));
        buf.writeInt(countToRemove);

        ClientPlayNetworking.send(ID, buf);
    }

    public static void removeItemStack(PlayerEntity player, Item itemToRemove, int count) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);

            if (stack.getItem() == itemToRemove && stack.getCount() >= count) {
                stack.decrement(count);
                if (stack.isEmpty()) {
                    player.getInventory().setStack(i, ItemStack.EMPTY);
                }
                break;
            }
        }
    }

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            Identifier itemId = buf.readIdentifier();
            int countToRemove = buf.readInt();

            server.execute( () -> {
                Item itemToRemove = Registries.ITEM.get(itemId);
                removeItemStack(player, itemToRemove, countToRemove);
                player.getInventory().markDirty();
            });
        });
    }
}
