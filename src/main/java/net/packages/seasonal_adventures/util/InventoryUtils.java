package net.packages.seasonal_adventures.util;

import net.minecraft.entity.player.PlayerEntity;
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
}

