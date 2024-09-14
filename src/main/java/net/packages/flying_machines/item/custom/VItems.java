package net.packages.flying_machines.item.custom;

import net.minecraft.item.Item;

public class VItems extends Item {
    private final int value;

    public VItems(Settings settings, int value) {
        super(settings);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}