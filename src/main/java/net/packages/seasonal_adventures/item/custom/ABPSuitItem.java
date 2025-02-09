package net.packages.seasonal_adventures.item.custom;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.util.Rarity;

public class ABPSuitItem extends ArmorItem {
    public ABPSuitItem(ArmorMaterial material, EquipmentType type, Settings settings) {
        super(material, type, settings);
        settings.rarity(Rarity.EPIC);
    }
}
