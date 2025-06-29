package net.flyingmylife.seasonal_adventures.datagen;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.flyingmylife.seasonal_adventures.datagen.provider.*;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.flyingmylife.seasonal_adventures.world.dimension.Dimensions;

public class SADataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(WorldGeneretor::new);
		pack.addProvider(BlockTagProvider::new);
		pack.addProvider(ItemTagProvider::new);
		pack.addProvider(LootTableProvider::new);
		if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) pack.addProvider(ModelProvider::new);
		pack.addProvider(RecipeProvider::new);
	}
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, Dimensions::bootstrapType);
	}
}
