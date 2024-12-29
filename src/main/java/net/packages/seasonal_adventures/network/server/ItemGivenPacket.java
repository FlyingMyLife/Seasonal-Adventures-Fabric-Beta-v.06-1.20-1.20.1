package net.packages.seasonal_adventures.network.server;
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
import net.packages.seasonal_adventures.SeasonalAdventures;

public class ItemGivenPacket {
    public static final Identifier ID = new Identifier(SeasonalAdventures.MOD_ID, "specific_item_given");

    public static void sendItemGivenRequest(Item itemToGive, int countToGive) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIdentifier(Registries.ITEM.getId(itemToGive));
        buf.writeInt(countToGive);

        ClientPlayNetworking.send(ID, buf);
    }
    public static void sendItemStackGivenRequest(ItemStack itemToGive, int countToGive) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIdentifier(Registries.ITEM.getId(itemToGive.getItem()));
        buf.writeInt(countToGive);

        ClientPlayNetworking.send(ID, buf);
    }

    public static void giveItems(PlayerEntity player, Item itemToGive, int countToGive) {
        PlayerInventory inventory = player.getInventory();

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);

            if (stack.getItem().equals(itemToGive) && stack.getCount() < stack.getMaxCount()) {
                int spaceLeft = stack.getMaxCount() - stack.getCount();
                int toAdd = Math.min(countToGive, spaceLeft);
                stack.increment(toAdd);
                countToGive -= toAdd;

                if (countToGive <= 0) {
                    return;
                }
            }
        }

        while (countToGive > 0) {
            int toAdd = Math.min(countToGive, itemToGive.getMaxCount());
            ItemStack newStack = new ItemStack(itemToGive, toAdd);
            if (!inventory.insertStack(newStack)) {
                player.dropItem(newStack, false);
            }
            countToGive -= toAdd;
        }
    }

    public static void register () {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            Identifier itemId = buf.readIdentifier();
            int countToGive = buf.readInt();

            server.execute(() -> {
                Item itemToRemove = Registries.ITEM.get(itemId);
                giveItems(player, itemToRemove, countToGive);
            });
        });
    }
}
