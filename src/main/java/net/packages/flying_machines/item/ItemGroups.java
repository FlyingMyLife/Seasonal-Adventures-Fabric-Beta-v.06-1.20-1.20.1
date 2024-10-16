package net.packages.flying_machines.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.packages.flying_machines.flying_machines;
import net.packages.flying_machines.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroups {
    public static final ItemGroup DYLAN_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(flying_machines.MOD_ID, "dylan_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.dylan_group"))
                    .icon(() -> new ItemStack(Items.DEACTIVATED_DYLAN)).entries((displayContext, entries) -> {
                        entries.add(Items.LOCKPICK);
                        entries.add(Items.SANCHEZ_GLASSES);
                        entries.add(Items.BEEF_TARTARE);
                        entries.add(Items.ATM);
                        entries.add(Items.V1);
                        entries.add(Items.V5);
                        entries.add(Items.V10);
                        entries.add(Items.V50);
                        entries.add(Items.V100);
                        entries.add(Items.V500);
                        entries.add(Items.V1000);
                        entries.add(Items.V10000);
                        entries.add(Items.RAW_TITANIUM);
                        entries.add(Items.CRUSHED_RAW_TITANIUM);
                        entries.add(Items.TITANIUM_INGOT);
                        entries.add(Items.TITANIUM_NUGGET);
                        entries.add(Items.TITANIUM_SHEET);
                        entries.add(Blocks.TITANIUM_BLOCK);
                        entries.add(Blocks.TITANIUM_ORE);
                        entries.add(Blocks.DEEPSLATE_TITANIUM_ORE);
                        entries.add(Items.GRADE_5_TITANIUM_INGOT);
                        entries.add(Items.GRADE_5_TITANIUM_SHEET);
                        entries.add(Blocks.GRADE_5_TITANIUM_BLOCK);
                        entries.add(Items.LITHIUM_INGOT);
                        entries.add(Items.RAW_LITHIUM);
                        entries.add(Blocks.LITHIUM_ORE);
                        entries.add(Blocks.DEEPSLATE_LITHIUM_ORE);
                        entries.add(Items.RAW_ALUMINIUM);
                        entries.add(Items.CRUSHED_RAW_ALUMINIUM);
                        entries.add(Items.ALUMINIUM_INGOT);
                        entries.add(Items.ALUMINIUM_NUGGET);
                        entries.add(Blocks.ALUMINIUM_ORE);
                        entries.add(Items.RAW_SILICON);
                        entries.add(Items.PURIFIED_SILICON);
                        entries.add(Blocks.DEEPSLATE_ALUMINIUM_ORE);
                        entries.add(Items.DEACTIVATED_DYLAN);
                        entries.add(Items.SOLID_AUTOMATON_CASING);
                        entries.add(Blocks.ADVANCED_AUTOMATON_BODY);
                        entries.add(Items.CENTRAL_AI_PROCESSOR);
                        entries.add(Items.DYLAN_MK1_SCHEME);
                        entries.add(Items.AUTOMATON_SKIN);
                        entries.add(Items.AUTOMATON_EYE_MATRIX);
                        entries.add(Items.LI_ON_BATTERY);
                        entries.add(Items.SYS_CABLE);
                        entries.add(Blocks.LAPTOP);
                    }).build());


    public static void registerItemGroups() {
        flying_machines.LOGGER.info("Registering Item Groups for " + flying_machines.MOD_ID);
    }
}
