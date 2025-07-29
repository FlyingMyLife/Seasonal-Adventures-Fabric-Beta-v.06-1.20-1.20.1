package net.flyingmylife.seasonal_adventures.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.flyingmylife.seasonal_adventures.SA;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


import net.minecraft.block.Block;
import net.flyingmylife.seasonal_adventures.block.SABlocks;
import net.flyingmylife.seasonal_adventures.item.SAItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for (Block block : Registries.BLOCK) {
            Identifier id = Registries.BLOCK.getId(block);
            if (id.getNamespace().equals(SA.MOD_ID) && !SABlocks.CUSTOM_MODELED_BLOCKS.contains(block)) {
                blockStateModelGenerator.registerSimpleCubeAll(block);
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(SAItems.V1, Models.GENERATED);
        generator.register(SAItems.V5, Models.GENERATED);
        generator.register(SAItems.V10, Models.GENERATED);
        generator.register(SAItems.V50, Models.GENERATED);
        generator.register(SAItems.V100, Models.GENERATED);
        generator.register(SAItems.V500, Models.GENERATED);
        generator.register(SAItems.V1000, Models.GENERATED);
        generator.register(SAItems.V10000, Models.GENERATED);
        generator.register(SAItems.AUTOMATON_SKIN, Models.GENERATED);
        generator.register(SAItems.SYS_CABLE, Models.GENERATED);
        generator.register(SAItems.DYLAN_MK1_SCHEME, Models.GENERATED);
        generator.register(SAItems.CENTRAL_AI_PROCESSOR, Models.GENERATED);
        generator.register(SAItems.TITANIUM_INGOT, Models.GENERATED);
        generator.register(SAItems.LITHIUM_INGOT, Models.GENERATED);
        generator.register(SAItems.LI_ON_BATTERY, Models.GENERATED);
        generator.register(SAItems.REFINED_TITANIUM_INGOT, Models.GENERATED);
        generator.register(SAItems.REFINED_TITANIUM_SHEET, Models.GENERATED);
        generator.register(SAItems.CARD, Models.GENERATED);
        generator.register(SAItems.TITANIUM_NUGGET, Models.GENERATED);
        generator.register(SAItems.TITANIUM_SHEET, Models.GENERATED);
        generator.register(SAItems.RAW_LITHIUM, Models.GENERATED);
        generator.register(SAItems.RAW_TITANIUM, Models.GENERATED);
        generator.register(SAItems.RAW_SILICON, Models.GENERATED);
        generator.register(SAItems.PURIFIED_SILICON, Models.GENERATED);
        generator.register(SAItems.ALUMINUM_INGOT, Models.GENERATED);
        generator.register(SAItems.ALUMINUM_NUGGET, Models.GENERATED);
        generator.register(SAItems.RAW_ALUMINUM, Models.GENERATED);
        generator.register(SAItems.ATM, Models.GENERATED);
        generator.register(SAItems.BEEF_TARTARE, Models.GENERATED);
        generator.register(SAItems.BIOCOMPONENT_OPTICAL_UNIT, Models.GENERATED);
        generator.register(SAItems.LOCKPICK, Models.GENERATED);
        generator.register(SAItems.SKINTH_OF_DREAMS, Models.GENERATED);
    }
}
