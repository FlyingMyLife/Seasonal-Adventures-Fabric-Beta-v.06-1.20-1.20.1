package net.packages.seasonal_adventures.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.block.SABlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SAItemGroups {
    public static final ItemGroup THE_LAST_REALITY = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(SeasonalAdventures.MOD_ID, "the_last_reality"),
            FabricItemGroup.builder().displayName(Text.translatable("item_group.seasonal_adventures.the_last_reality"))
                    .icon(() -> new ItemStack(SAItems.DEACTIVATED_DYLAN)).entries((displayContext, entries) -> {
                        entries.add(SAItems.LOCKPICK);
                        entries.add(SAItems.COPPER_LOCK);
                        entries.add(SAItems.IRON_LOCK);
                        entries.add(SAItems.SANCHEZ_GLASSES);
                        entries.add(SAItems.BEEF_TARTARE);
                        entries.add(SAItems.ATM);
                        entries.add(SAItems.V1);
                        entries.add(SAItems.V5);
                        entries.add(SAItems.V10);
                        entries.add(SAItems.V50);
                        entries.add(SAItems.V100);
                        entries.add(SAItems.V500);
                        entries.add(SAItems.V1000);
                        entries.add(SAItems.V10000);
                        entries.add(SAItems.RAW_TITANIUM);
                        entries.add(SAItems.TITANIUM_INGOT);
                        entries.add(SAItems.TITANIUM_NUGGET);
                        entries.add(SAItems.TITANIUM_SHEET);
                        entries.add(SABlocks.TITANIUM_BLOCK);
                        entries.add(SABlocks.TITANIUM_ORE);
                        entries.add(SABlocks.DEEPSLATE_TITANIUM_ORE);
                        entries.add(SAItems.GRADE_5_TITANIUM_INGOT);
                        entries.add(SAItems.GRADE_5_TITANIUM_SHEET);
                        entries.add(SABlocks.GRADE_5_TITANIUM_BLOCK);
                        entries.add(SAItems.LITHIUM_INGOT);
                        entries.add(SAItems.RAW_LITHIUM);
                        entries.add(SABlocks.LITHIUM_ORE);
                        entries.add(SABlocks.DEEPSLATE_LITHIUM_ORE);
                        entries.add(SAItems.RAW_ALUMINUM);
                        entries.add(SAItems.ALUMINUM_INGOT);
                        entries.add(SAItems.ALUMINUM_NUGGET);
                        entries.add(SABlocks.ALUMINIUM_ORE);
                        entries.add(SAItems.RAW_SILICON);
                        entries.add(SAItems.PURIFIED_SILICON);
                        entries.add(SABlocks.DEEPSLATE_ALUMINIUM_ORE);
                        entries.add(SAItems.DEACTIVATED_DYLAN);
                        entries.add(SAItems.SOLID_AUTOMATON_CASING);
                        entries.add(SABlocks.ADVANCED_AUTOMATON_BODY);
                        entries.add(SAItems.CENTRAL_AI_PROCESSOR);
                        entries.add(SAItems.DYLAN_MK1_SCHEME);
                        entries.add(SAItems.AUTOMATON_SKIN);
                        entries.add(SAItems.BIOCOMPONENT_OPTICAL_UNIT);
                        entries.add(SAItems.LI_ON_BATTERY);
                        entries.add(SAItems.SYS_CABLE);
                        entries.add(SABlocks.LAPTOP);
                    }).build());

    public static final ItemGroup THE_HEART_OF_UNIVERSE = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(SeasonalAdventures.MOD_ID, "the_heart_of_universe"),
            FabricItemGroup.builder().displayName(Text.translatable("item_group.seasonal_adventures.the_heart_of_universe"))
                    .icon(() -> new ItemStack(SAItems.SKINTH_OF_DREAMS)).entries((displayContext, entries) -> {
                        entries.add(new ItemStack(SAItems.SKINTH_OF_DREAMS));
                    }).build());


    public static void registerItemGroups() {
        SeasonalAdventures.LOGGER.info("Registering Item Groups for " + SeasonalAdventures.MOD_ID);
    }
}
