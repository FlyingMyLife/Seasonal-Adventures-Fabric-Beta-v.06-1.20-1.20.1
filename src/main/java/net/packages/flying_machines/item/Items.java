package net.packages.flying_machines.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Rarity;
import net.packages.flying_machines.entity.Entities;
import net.packages.flying_machines.flying_machines;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.packages.flying_machines.item.custom.*;

public class Items {
    public static final Item ABP_HELMET = registerItem("abp_helmet", new ABPSuitItem(ArmorMaterials.ActiveBiologicalProtectionSuit, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item ABP_CHESTPLATE = registerItem("abp_chestplate", new ABPSuitItem(ArmorMaterials.ActiveBiologicalProtectionSuit, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item ABP_LEGGINGS = registerItem("abp_leggings", new ABPSuitItem(ArmorMaterials.ActiveBiologicalProtectionSuit, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ABP_BOOTS = registerItem("abp_boots", new ABPSuitItem(ArmorMaterials.ActiveBiologicalProtectionSuit, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item AUTOMATON_SKIN = registerItem("automaton_skin", new Item(new FabricItemSettings()));
    public static final Item V1 = registerItem("1v", new VItems(new FabricItemSettings(), 1));
    public static final Item V5 = registerItem("5v", new VItems(new FabricItemSettings(), 5));
    public static final Item V10 = registerItem("10v", new VItems(new FabricItemSettings().rarity(Rarity.UNCOMMON), 10));
    public static final Item V50 = registerItem("50v", new VItems(new FabricItemSettings().rarity(Rarity.UNCOMMON), 50));
    public static final Item V100 = registerItem("100v", new VItems(new FabricItemSettings().rarity(Rarity.RARE), 100));
    public static final Item V500 = registerItem("500v", new VItems(new FabricItemSettings().rarity(Rarity.RARE), 500));
    public static final Item V1000 = registerItem("1000v", new VItems(new FabricItemSettings().rarity(Rarity.EPIC), 1000));
    public static final Item V10000 = registerItem("10000v", new VItems(new FabricItemSettings().rarity(Rarity.EPIC), 10000));
    public static final Item SYS_CABLE = registerItem("sys_cable", new Item(new FabricItemSettings()));
    public static final Item DYLAN_MK1_SCHEME = registerItem("dylan_mk1_scheme", new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item CENTRAL_AI_PROCESSOR = registerItem("central_ai_processor", new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).fireproof()));
    public static final Item TITANIUM_INGOT = registerItem("titanium_ingot", new Item(new FabricItemSettings()));
    public static final Item LITHIUM_INGOT = registerItem("lithium_ingot", new Item(new FabricItemSettings()));
    public static final Item LI_ON_BATTERY = registerItem("li-on_battery", new Item(new FabricItemSettings()));
    public static final Item GRADE_5_TITANIUM_INGOT = registerItem("grade_5_titanium_ingot", new Item(new FabricItemSettings()));
    public static final Item GRADE_5_TITANIUM_SHEET = registerItem("grade_5_titanium_sheet", new Item(new FabricItemSettings()));
    public static final Item SOLID_AUTOMATON_CASING = registerItem("solid_automaton_casing", new Item(new FabricItemSettings()));
    public static final Item SANCHEZ_GLASSES = registerItem("sanchez_glasses", new SanchezGlasses(new FabricItemSettings().equipmentSlot(stack -> EquipmentSlot.HEAD).rarity(Rarity.EPIC)));
    public static final Item CARD = registerItem("card", new CardItem(new FabricItemSettings().rarity(Rarity.EPIC).maxCount(1).fireproof()));
    public static final Item TITANIUM_NUGGET = registerItem("titanium_nugget", new Item(new FabricItemSettings()));
    public static final Item TITANIUM_SHEET = registerItem("titanium_sheet", new Item(new FabricItemSettings()));
    public static final Item RAW_LITHIUM = registerItem("raw_lithium", new Item(new FabricItemSettings()));
    public static final Item RAW_TITANIUM = registerItem("raw_titanium", new Item(new FabricItemSettings()));
    public static final Item CRUSHED_RAW_TITANIUM = registerItem("crushed_raw_titanium", new Item(new FabricItemSettings()));
    public static final Item RAW_SILICON = registerItem("raw_silicon", new Item(new FabricItemSettings()));
    public static final Item PURIFIED_SILICON = registerItem("purified_silicon", new Item(new FabricItemSettings()));
    public static final Item ALUMINIUM_INGOT = registerItem("aluminium_ingot", new Item(new FabricItemSettings()));
    public static final Item ALUMINIUM_NUGGET = registerItem("aluminium_nugget", new Item(new FabricItemSettings()));
    public static final Item RAW_ALUMINIUM = registerItem("raw_aluminium", new Item(new FabricItemSettings()));
    public static final Item CRUSHED_RAW_ALUMINIUM = registerItem("crushed_raw_aluminium", new Item(new FabricItemSettings()));
    public static final Item DEACTIVATED_DYLAN = registerItem("deactivated_dylan", new SpawnEggItem(Entities.DYLAN, 0x86518, 0x3b260f, new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC).fireproof()));
    public static final Item ATM = registerItem("atm", new AtmItem(new FabricItemSettings(), Entities.ATM));
    public static final Item BEEF_TARTARE = registerItem("beef_tartare", new Item(new FabricItemSettings().food(FoodComponents.BEEF_TARTARE)));
    public static final Item AUTOMATON_EYE_MATRIX = registerItem("automaton_eye_matrix", new Item(new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item LOCKPICK= registerItem("lockpick", new Item(new FabricItemSettings().rarity(Rarity.RARE)));
    private static void addItemsToFoodAndDrinkItemGroup(FabricItemGroupEntries entries) {
    }

    private static Item registerItem(String name, Item item) {
            return Registry.register(Registries.ITEM, new Identifier(flying_machines.MOD_ID, name), item);
    }

    public static void registerModItems() {
        flying_machines.LOGGER.info("Registering Mod Items for " + flying_machines.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(Items::addItemsToFoodAndDrinkItemGroup);

    }
}

