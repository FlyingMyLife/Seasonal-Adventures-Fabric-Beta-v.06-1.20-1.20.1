package dev.flyingmylife.seasonal_adventures.network.packet.c2s;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;

import static dev.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes.C2S.RemoveItemPayload;
public class RemoveItemPacket {

    public static void removeItem(Item item, int count) {
        ClientPlayNetworking.send(new RemoveItemPayload(Registries.ITEM.getId(item), count));
    }

    private static void removeItem(PlayerEntity player, Item itemToRemove, int count) {
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

    public static void register(RemoveItemPayload payload, ServerPlayNetworking.Context context) {

        Item item = Registries.ITEM.get(payload.itemId());
        removeItem(context.player(), item, payload.amount());
        context.player().getInventory().markDirty();
    }
}
