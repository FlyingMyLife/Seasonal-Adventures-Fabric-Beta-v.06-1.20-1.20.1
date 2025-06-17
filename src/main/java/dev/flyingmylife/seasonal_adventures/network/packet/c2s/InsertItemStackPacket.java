package dev.flyingmylife.seasonal_adventures.network.packet.c2s;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;

import static dev.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes.C2S.InsertItemStackPayload;

public class InsertItemStackPacket {

    public static void insertItems(Item item, int count) {
        ClientPlayNetworking.send(new InsertItemStackPayload(Registries.ITEM.getId(item), count));
    }

    private static void insertItems(PlayerEntity player, Item item, int amount) {
        int maxStackSize = item.getMaxCount();

        while (amount > 0) {
            int stackSize = Math.min(amount, maxStackSize);
            ItemStack stack = new ItemStack(item, stackSize);

            if (!player.getInventory().insertStack(stack)) {
                player.dropItem(stack, false);
            }

            amount -= stackSize;
        }
    }

    public static void register(InsertItemStackPayload payload, ServerPlayNetworking.Context context) {
        Item item = Registries.ITEM.get(payload.itemId());
        insertItems(context.player(), item, payload.amount());
    }
}
