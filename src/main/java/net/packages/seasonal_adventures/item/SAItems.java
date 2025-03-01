package net.packages.seasonal_adventures.item;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.entity.SAEntities;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.item.custom.*;

import java.util.Arrays;
import java.util.List;

public class SAItems {

    //Замки
    public static final Item COPPER_LOCK = registerItem("copper_lock", new LockItem(new Item.Settings(), 0));
    public static final Item IRON_LOCK = registerItem("iron_lock", new LockItem(new Item.Settings(), 1));
    public static final Item GOLD_LOCK = registerItem("gold_lock", new LockItem(new Item.Settings(), 2));

    //Броня ABPS [Active biological protection suit]
    //public static final Item ABP_HELMET = registerItem("abp_helmet", new ABPSuitItem(ArmorMaterials.ActiveBiologicalProtectionSuit, ArmorItem.Type.HELMET, new Item.Settings()));
    //public static final Item ABP_CHESTPLATE = registerItem("abp_chestplate", new ABPSuitItem(ArmorMaterials.ActiveBiologicalProtectionSuit, ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    //public static final Item ABP_LEGGINGS = registerItem("abp_leggings", new ABPSuitItem(ArmorMaterials.ActiveBiologicalProtectionSuit, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    //public static final Item ABP_BOOTS = registerItem("abp_boots", new ABPSuitItem(ArmorMaterials.ActiveBiologicalProtectionSuit, EquipmentType.BOOTS, new Item.Settings()));

    //V предметы
    public static final Item V1 = registerItem("1v", new Item(new Item.Settings()));
    public static final Item V5 = registerItem("5v", new Item(new Item.Settings()));
    public static final Item V10 = registerItem("10v", new Item(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final Item V50 = registerItem("50v", new Item(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final Item V100 = registerItem("100v", new Item(new Item.Settings().rarity(Rarity.RARE)));
    public static final Item V500 = registerItem("500v", new Item(new Item.Settings().rarity(Rarity.RARE)));
    public static final Item V1000 = registerItem("1000v", new Item(new Item.Settings().rarity(Rarity.EPIC)));
    public static final Item V10000 = registerItem("10000v", new Item(new Item.Settings().rarity(Rarity.EPIC)));

    public static final Item AUTOMATON_SKIN = registerItem("automaton_skin", new Item(new Item.Settings()));
    public static final Item SYS_CABLE = registerItem("sys_cable", new Item(new Item.Settings()));
    public static final Item DYLAN_MK1_SCHEME = registerItem("dylan_mk1_scheme", new Item(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item CENTRAL_AI_PROCESSOR = registerItem("central_ai_processor", new Item(new Item.Settings().maxCount(1).rarity(Rarity.RARE).fireproof()));
    public static final Item TITANIUM_INGOT = registerItem("titanium_ingot", new Item(new Item.Settings()));
    public static final Item LITHIUM_INGOT = registerItem("lithium_ingot", new Item(new Item.Settings()));
    public static final Item LI_ON_BATTERY = registerItem("li-on_battery", new Item(new Item.Settings()));
    public static final Item GRADE_5_TITANIUM_INGOT = registerItem("grade_5_titanium_ingot", new Item(new Item.Settings()));
    public static final Item GRADE_5_TITANIUM_SHEET = registerItem("grade_5_titanium_sheet", new Item(new Item.Settings()));
    public static final Item SOLID_AUTOMATON_CASING = registerItem("solid_automaton_casing", new Item(new Item.Settings()));
    public static final Item SANCHEZ_GLASSES = registerItem("sanchez_glasses", new SanchezGlassesItem(new Item.Settings().equippable(EquipmentSlot.HEAD).rarity(Rarity.EPIC)));
    public static final Item CARD = registerItem("card", new CardItem(new Item.Settings().rarity(Rarity.EPIC).maxCount(1).fireproof()));
    public static final Item TITANIUM_NUGGET = registerItem("titanium_nugget", new Item(new Item.Settings()));
    public static final Item TITANIUM_SHEET = registerItem("titanium_sheet", new Item(new Item.Settings()));
    public static final Item RAW_LITHIUM = registerItem("raw_lithium", new Item(new Item.Settings()));
    public static final Item RAW_TITANIUM = registerItem("raw_titanium", new Item(new Item.Settings()));public static final Item RAW_SILICON = registerItem("raw_silicon", new Item(new Item.Settings()));
    public static final Item PURIFIED_SILICON = registerItem("purified_silicon", new Item(new Item.Settings()));
    public static final Item ALUMINUM_INGOT = registerItem("aluminium_ingot", new Item(new Item.Settings()));
    public static final Item ALUMINUM_NUGGET = registerItem("aluminium_nugget", new Item(new Item.Settings()));
    public static final Item RAW_ALUMINUM = registerItem("raw_aluminium", new Item(new Item.Settings()));
    //public static final Item DEACTIVATED_DYLAN = registerItem("deactivated_dylan", new SpawnEggItem(SAEntities.DYLAN, 0x86518, 0x3b260f, new Item.Settings().maxCount(1).rarity(Rarity.EPIC).fireproof()));
    public static final Item ATM = registerItem("atm", new AtmItem(new Item.Settings().maxCount(1), SAEntities.ATM));
    public static final Item BEEF_TARTARE = registerItem("beef_tartare", new Item(new Item.Settings().food(FoodComponents.BEEF_TARTARE)));
    public static final Item BIOCOMPONENT_OPTICAL_UNIT = registerItem("biocomponent_optical_unit", new Item(new Item.Settings().rarity(Rarity.RARE)));
    public static final Item LOCKPICK= registerItem("lockpick", new Item(new Item.Settings().maxCount(16).rarity(Rarity.RARE)));
    public static final Item SKINTH_OF_DREAMS = registerItem("skinth_of_dreams", new SkinthOfDreamsItem(new Item.Settings()));

    public static final List<Item> SIMPLE_ITEMS = Arrays.asList(
            V1, V5, V10, V50, V100, V500, V1000, V10000, AUTOMATON_SKIN,
            SYS_CABLE, CENTRAL_AI_PROCESSOR, TITANIUM_INGOT, LITHIUM_INGOT,
            LI_ON_BATTERY, GRADE_5_TITANIUM_INGOT, GRADE_5_TITANIUM_SHEET, CARD,
            TITANIUM_NUGGET, TITANIUM_SHEET, RAW_LITHIUM, RAW_TITANIUM, PURIFIED_SILICON,
            ALUMINUM_INGOT, ALUMINUM_NUGGET, RAW_ALUMINUM, ATM, BEEF_TARTARE,
            BIOCOMPONENT_OPTICAL_UNIT, LOCKPICK, SKINTH_OF_DREAMS, RAW_SILICON
    );

    private static Item registerItem(String name, Item item) {
            return Registry.register(Registries.ITEM, Identifier.of(SeasonalAdventures.MOD_ID, name), item);
    }

    public static void registerItems() {
        SeasonalAdventures.LOGGER.info("Registering ItemTags for " + SeasonalAdventures.MOD_ID);
    }
}

