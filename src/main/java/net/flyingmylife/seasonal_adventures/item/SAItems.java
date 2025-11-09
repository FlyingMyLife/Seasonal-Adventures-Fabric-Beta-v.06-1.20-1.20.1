package net.flyingmylife.seasonal_adventures.item;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.item.custom.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Rarity;
import net.flyingmylife.seasonal_adventures.entity.SAEntities;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class SAItems {

    public static final Item COPPER_LOCK;
    public static final Item IRON_LOCK;
    public static final Item GOLD_LOCK;
    public static final Item KEY;

    public static final Item V1;
    public static final Item V5;
    public static final Item V10;
    public static final Item V50;
    public static final Item V100;
    public static final Item V500;
    public static final Item V1000;
    public static final Item V10000;

    public static final Item AUTOMATON_SKIN;
    public static final Item SYS_CABLE;
    public static final Item DYLAN_MK1_SCHEME;
    public static final Item CENTRAL_AI_PROCESSOR;
    public static final Item TITANIUM_INGOT;
    public static final Item LITHIUM_INGOT;
    public static final Item LI_ON_BATTERY;
    public static final Item REFINED_TITANIUM_INGOT;
    public static final Item REFINED_TITANIUM_SHEET;
    public static final Item SOLID_AUTOMATON_CASING;
    public static final Item SANCHEZ_GLASSES;
    public static final Item CARD;
    public static final Item TITANIUM_NUGGET;
    public static final Item TITANIUM_SHEET;
    public static final Item RAW_LITHIUM;
    public static final Item RAW_TITANIUM;
    public static final Item RAW_SILICON;
    public static final Item PURIFIED_SILICON;
    public static final Item ALUMINUM_INGOT;
    public static final Item ALUMINUM_NUGGET;
    public static final Item RAW_ALUMINUM;

    public static final Item ATM;
    public static final Item BEEF_TARTARE;
    public static final Item BIOCOMPONENT_OPTICAL_UNIT;
    public static final Item LOCKPICK;
    public static final Item SKINTH_OF_DREAMS;

    public static void registerItems() {
        SA.LOGGER.info("Registering items for Seasonal Adventures");
    }

    public static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(Registries.ITEM.getKey(), Identifier.of(SA.MOD_ID, name));
        Item item = factory.apply(settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, key, item);
    }

    static {
        COPPER_LOCK = register("copper_lock", LockItem::new, new Item.Settings());
        IRON_LOCK = register("iron_lock", s -> new LockItem(s, 1), new Item.Settings());
        GOLD_LOCK = register("gold_lock", s -> new LockItem(s, 2), new Item.Settings());
        KEY = register("key", KeyItem::new, new Item.Settings());

        V1 = register("1v", Item::new, new Item.Settings());
        V5 = register("5v", Item::new, new Item.Settings());
        V10 = register("10v", Item::new, new Item.Settings().rarity(Rarity.UNCOMMON));
        V50 = register("50v", Item::new, new Item.Settings().rarity(Rarity.UNCOMMON));
        V100 = register("100v", Item::new, new Item.Settings().rarity(Rarity.RARE));
        V500 = register("500v", Item::new, new Item.Settings().rarity(Rarity.RARE));
        V1000 = register("1000v", Item::new, new Item.Settings().rarity(Rarity.EPIC));
        V10000 = register("10000v", Item::new, new Item.Settings().rarity(Rarity.EPIC));

        AUTOMATON_SKIN = register("automaton_skin", Item::new, new Item.Settings());
        SYS_CABLE = register("sys_cable", Item::new, new Item.Settings());
        DYLAN_MK1_SCHEME = register("dylan_mk1_scheme", Item::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
        CENTRAL_AI_PROCESSOR = register("central_ai_processor", Item::new, new Item.Settings().maxCount(1).rarity(Rarity.RARE).fireproof());
        TITANIUM_INGOT = register("titanium_ingot", Item::new, new Item.Settings());
        LITHIUM_INGOT = register("lithium_ingot", Item::new, new Item.Settings());
        LI_ON_BATTERY = register("li-on_battery", Item::new, new Item.Settings());
        REFINED_TITANIUM_INGOT = register("refined_titanium_ingot", Item::new, new Item.Settings());
        REFINED_TITANIUM_SHEET = register("refined_titanium_sheet", Item::new, new Item.Settings());
        SOLID_AUTOMATON_CASING = register("solid_automaton_casing", Item::new, new Item.Settings());
        SANCHEZ_GLASSES = register("sanchez_glasses", SanchezGlassesItem::new, new Item.Settings().maxCount(1).equipmentSlot((entity, stack) -> EquipmentSlot.HEAD).rarity(Rarity.EPIC));
        CARD = register("card", CardItem::new, new Item.Settings().rarity(Rarity.EPIC).maxCount(1).fireproof());
        TITANIUM_NUGGET = register("titanium_nugget", Item::new, new Item.Settings());
        TITANIUM_SHEET = register("titanium_sheet", Item::new, new Item.Settings());
        RAW_LITHIUM = register("raw_lithium", Item::new, new Item.Settings());
        RAW_TITANIUM = register("raw_titanium", Item::new, new Item.Settings());
        RAW_SILICON = register("raw_silicon", Item::new, new Item.Settings());
        PURIFIED_SILICON = register("purified_silicon", Item::new, new Item.Settings());
        ALUMINUM_INGOT = register("aluminium_ingot", Item::new, new Item.Settings());
        ALUMINUM_NUGGET = register("aluminium_nugget", Item::new, new Item.Settings());
        RAW_ALUMINUM = register("raw_aluminium", Item::new, new Item.Settings());

        ATM = register("atm", s -> new AtmItem(s.maxCount(1), SAEntities.ATM), new Item.Settings());
        BEEF_TARTARE = register("beef_tartare", Item::new, new Item.Settings().food(SAFoodComponents.BEEF_TARTARE));
        BIOCOMPONENT_OPTICAL_UNIT = register("biocomponent_optical_unit", s -> new BiocomponentItem(s.rarity(Rarity.RARE), "#b9o6u"), new Item.Settings());
        LOCKPICK = register("lockpick", Item::new, new Item.Settings().maxCount(16).rarity(Rarity.RARE));
        SKINTH_OF_DREAMS = register("skinth_of_dreams", SkinthOfDreamsItem::new, new Item.Settings());
    }
}
