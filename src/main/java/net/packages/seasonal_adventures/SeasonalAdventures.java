package net.packages.seasonal_adventures;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.packages.seasonal_adventures.block.Blocks;
import net.packages.seasonal_adventures.block.entity.BlockEntities;
import net.packages.seasonal_adventures.entity.Entities;
import net.packages.seasonal_adventures.entity.custom.ATMEntity;
import net.packages.seasonal_adventures.event.EventHandler;
import net.packages.seasonal_adventures.gui.screen.ConfigScreen;
import net.packages.seasonal_adventures.item.ItemGroups;
import net.packages.seasonal_adventures.item.Items;
import net.packages.seasonal_adventures.network.client.ConfigSyncPacket;
import net.packages.seasonal_adventures.network.server.*;
import net.packages.seasonal_adventures.particle.Particles;
import net.packages.seasonal_adventures.sound.Sounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeasonalAdventures implements ModInitializer {
	public static final String MOD_ID = "seasonal_adventures";
	public static final Logger LOGGER = LoggerFactory.getLogger("Seasonal Adventures");
	public static boolean isCandlelightInstalled = FabricLoader.getInstance().isModLoaded("candlelight");
	public static final RegistryKey<PlacedFeature> LARGE_TITANIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "ore_titanium_large"));
	public static final RegistryKey<PlacedFeature> SMALL_TITANIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "ore_titanium_small"));
	public static final RegistryKey<PlacedFeature> LARGE_ALUMINIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "ore_aluminium_large"));
	public static final RegistryKey<PlacedFeature> SMALL_ALUMINIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "ore_aluminium_small"));
	public static final RegistryKey<PlacedFeature> LARGE_LITHIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "ore_lithium_large"));
	public static final RegistryKey<PlacedFeature> SMALL_LITHIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, "ore_lithium_small"));

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Modding!");
		MidnightConfig.init(MOD_ID, ConfigScreen.class);
		EventHandler.initialize();
//		ResourceConditions.register();

		ApplyStatusEffectPacket.register();
		DimensionOfDreamsGeneratorPacket.register();
		ConfigSyncPacket.registerServerPacket();
		RestoreChestPacket.register();
		SpecificItemRemovalPacket.register();
		ItemGivenPacket.register();
		ItemRemovalPacket.register();
		BankingOperationsPacket.register();
		LoadChunkPacket.register();

		Particles.registerParticles();
		BlockEntities.registerBlockEntities();
		Sounds.registerSounds();
		ItemGroups.registerItemGroups();
		Items.registerModItems();
		Blocks.registerModBlocks();



		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, LARGE_TITANIUM_ORE_PLACED_KEY);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, SMALL_TITANIUM_ORE_PLACED_KEY);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, LARGE_ALUMINIUM_ORE_PLACED_KEY);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, SMALL_ALUMINIUM_ORE_PLACED_KEY);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, LARGE_LITHIUM_ORE_PLACED_KEY);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, SMALL_LITHIUM_ORE_PLACED_KEY);

		FabricDefaultAttributeRegistry.register(Entities.ATM, ATMEntity.createATMAttributes());
	}
}