package net.packages.seasonal_adventures.util.game;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class InventoryUtils {

    public static int getItemAmount(PlayerEntity player, ItemStack itemStack) {
        DefaultedList<ItemStack> inventory = player.getInventory().main;
        int totalAmount = 0;
        for (ItemStack stack : inventory) {
            if (stack.isOf(itemStack.getItem())) {
                totalAmount += stack.getCount();
            }
        }

        return totalAmount;
    }
    public static boolean hasFreeSlot(PlayerEntity player) {
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.getStack(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}

