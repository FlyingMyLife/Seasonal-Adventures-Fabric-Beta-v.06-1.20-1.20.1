package net.packages.seasonal_adventures;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.packages.lumina_lore.LuminaLore;
import net.packages.seasonal_adventures.block.Blocks;
import net.packages.seasonal_adventures.block.entity.ModBlockEntities;
import net.packages.seasonal_adventures.entity.Entities;
import net.packages.seasonal_adventures.entity.custom.ATMEntity;
import net.packages.seasonal_adventures.entity.custom.DylanEntity;
import net.packages.seasonal_adventures.events.EventHandler;
import net.packages.seasonal_adventures.events.JDBCardHandler;
import net.packages.seasonal_adventures.gui.handlers.*;
import net.packages.seasonal_adventures.item.ItemGroups;
import net.packages.seasonal_adventures.item.Items;
import net.packages.seasonal_adventures.network.*;
import net.packages.seasonal_adventures.recipe.BeefTartareRecipeCondition;
import net.packages.seasonal_adventures.sound.Sounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class SeasonalAdventures implements ModInitializer {
	public static final GameRules.Key<GameRules.BooleanRule> IS_ATMS_BREAKABLE = GameRuleRegistry.register("isAtmsBreakable", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<GameRules.BooleanRule> SaDebug = GameRuleRegistry.register("sa:debug", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false));
	public static final Identifier MAIN_LAB_CHEST = new Identifier("seasonal_adventures", "chests/main_lab_chest");
	public static final Identifier TITANIUM_CHEST_LOOT_TABLE = new Identifier("seasonal_adventures", "chests/titanium_chest");
	public static final Identifier ALUMINIUM_CHEST_LOOT_TABLE = new Identifier("seasonal_adventures", "chests/aluminium_chest");

	public static final ScreenHandlerType<LockpickScreenHandler> LOCKPICK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("seasonal_adventures", "lockpick_screen"), LockpickScreenHandler::new);
	public static final ScreenHandlerType<DylanScreenHandler> DYLAN_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("seasonal_adventures", "dylan_screen"), DylanScreenHandler::new);
	public static final ScreenHandlerType<ATMScreenHandler> ATM_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("seasonal_adventures", "atm_screen"), ATMScreenHandler::new);
	public static final ScreenHandlerType<DylanSettingsScreenHandler> DYLAN_SETTINGS_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("seasonal_adventures", "dylan_settings_screen"), DylanSettingsScreenHandler::new);

	public static boolean isCandlelightInstalled = FabricLoader.getInstance().isModLoaded("candlelight");

	public static final RegistryKey<PlacedFeature> LARGE_TITANIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("seasonal_adventures", "ore_titanium_large"));
	public static final RegistryKey<PlacedFeature> SMALL_TITANIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("seasonal_adventures", "ore_titanium_small"));
	public static final RegistryKey<PlacedFeature> LARGE_ALUMINIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("seasonal_adventures", "ore_aluminium_large"));
	public static final RegistryKey<PlacedFeature> SMALL_ALUMINIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("seasonal_adventures", "ore_aluminium_small"));
	public static final RegistryKey<PlacedFeature> LARGE_LITHIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("seasonal_adventures", "ore_lithium_large"));
	public static final RegistryKey<PlacedFeature> SMALL_LITHIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("seasonal_adventures", "ore_lithium_small"));
	public static final String MOD_ID = "seasonal_adventures";

	public static final Logger LOGGER = LoggerFactory.getLogger("Seasonal Adventures");

	public static void sendDebugMessage (String log, PlayerEntity player) {
		if (player.getWorld().getGameRules().getBoolean(SeasonalAdventures.SaDebug)){
		final Text debug = Text.literal("[DEBUG] ").formatted(Formatting.BOLD, Formatting.GRAY);
		final Text sa = Text.literal("Seasonal Adventures: ").formatted(Formatting.AQUA);
		final Text message = Text.literal(log).formatted(Formatting.ITALIC);
		player.sendMessage(((MutableText) debug).append(sa).append(message));
		}
	}
	@Override
	public void onInitialize() {
		LuminaLore.initialize();

		ServerWorldEvents.LOAD.register((world, server) -> {
			MinecraftServer minecraftServer = server.getServer();
			JDBCardHandler.initialize(minecraftServer, world.getOverworld());
		});
		GeckoLib.initialize();

		EventHandler.initialize();

		ResourceConditions.register(new Identifier(MOD_ID, "beef_tartare_condition"), BeefTartareRecipeCondition::shouldLoad);
		RestoreChestPacket.register();
		SpecificItemRemovalPacket.register();
		ItemGivenPacket.register();
		ItemRemovalPacket.register();
		CardGivenPacket.register();
		BankingOperationsPacket.register();

		ModBlockEntities.registerBlockEntities();

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

		FabricDefaultAttributeRegistry.register((EntityType<? extends LivingEntity>) Entities.DYLAN, DylanEntity.createDylanAttributes());
		FabricDefaultAttributeRegistry.register((EntityType<? extends LivingEntity>) Entities.ATM, ATMEntity.createATMAttributes());
	}
}