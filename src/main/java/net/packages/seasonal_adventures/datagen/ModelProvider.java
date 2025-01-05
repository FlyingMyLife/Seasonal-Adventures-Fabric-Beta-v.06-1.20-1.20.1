package net.packages.seasonal_adventures.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.packages.seasonal_adventures.block.Blocks;
import net.packages.seasonal_adventures.item.Items;
import net.packages.seasonal_adventures.tag.ItemTags;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        Blocks.SIMPLE_BLOCKS.forEach((blockStateModelGenerator::registerSimpleCubeAll));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        Items.SIMPLE_ITEMS.forEach((item -> {
            itemModelGenerator.register(item, Models.GENERATED);
        }));
    }
}
