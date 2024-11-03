package net.packages.seasonal_adventures.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.packages.seasonal_adventures.block.Blocks;

public class LootTableProvider extends FabricBlockLootTableProvider {
    public LootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {

    }
}
