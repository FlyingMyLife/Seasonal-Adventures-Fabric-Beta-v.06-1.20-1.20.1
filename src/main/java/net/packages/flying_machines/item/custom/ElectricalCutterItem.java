package net.packages.flying_machines.item.custom;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.packages.flying_machines.item.Items;

public class ElectricalCutterItem extends Item {
    private static final int MAX_DURABILITY = 25;

    public ElectricalCutterItem(Settings settings) {
        super(settings.maxDamage(MAX_DURABILITY));
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        if (stack.getDamage() >= MAX_DURABILITY) {
            stack.setDamage(MAX_DURABILITY - 1); // Prevent the item from breaking completely
        }
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return stack.getDamage() > 0;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isOf(Items.LI_ON_BATTERY);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        if (stack.getDamage() < MAX_DURABILITY - 1) {
            ItemStack newStack = stack.copy();
            newStack.setDamage(stack.getDamage() + 1);
            return newStack;
        } else {
            return ItemStack.EMPTY; // If at max durability, prevent further crafting
        }
    }

    @Override
    public boolean hasRecipeRemainder() {
        return true;
    }

}
