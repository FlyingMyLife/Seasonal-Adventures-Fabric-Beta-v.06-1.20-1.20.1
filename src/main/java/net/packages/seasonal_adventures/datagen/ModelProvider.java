package net.packages.seasonal_adventures.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.ArmorItem;
import net.packages.seasonal_adventures.item.Items;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // blockStateModelGenerator.registerSimpleCubeAll(Blocks.block);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // itemModelGenerator.register(Items.item);
        itemModelGenerator.registerArmor((ArmorItem) Items.ABP_BOOTS);
        itemModelGenerator.registerArmor((ArmorItem) Items.ABP_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) Items.ABP_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) Items.ABP_HELMET);
    }
}
