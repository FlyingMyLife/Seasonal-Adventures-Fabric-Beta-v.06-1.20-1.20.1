package net.packages.seasonal_adventures.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.packages.seasonal_adventures.block.SABlocks;
import net.packages.seasonal_adventures.item.SAItems;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        SABlocks.SIMPLE_BLOCKS.forEach((blockStateModelGenerator::registerSimpleCubeAll));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        SAItems.SIMPLE_ITEMS.forEach((item -> {
            itemModelGenerator.register(item, Models.GENERATED);
        }));
    }
}
