package net.packages.flying_machines;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.packages.flying_machines.block.Blocks;
import net.packages.flying_machines.block.entity.ModBlockEntities;
import net.packages.flying_machines.command.DebugLockCommand;
import net.packages.flying_machines.entity.Entities;
import net.packages.flying_machines.entity.custom.ATMEntity;
import net.packages.flying_machines.entity.custom.DylanEntity;
import net.packages.flying_machines.events.ModEventHandler;
import net.packages.flying_machines.events.PlayerCardHandler;
import net.packages.flying_machines.gui.*;
import net.packages.flying_machines.item.ItemGroups;
import net.packages.flying_machines.item.Items;
import net.packages.flying_machines.item.custom.VItems;
import net.packages.flying_machines.network.CardGivenPacket;
import net.packages.flying_machines.network.ItemRemovalPacket;
import net.packages.flying_machines.network.SpecificItemRemovalPacket;
import net.packages.flying_machines.recipe.BeefTartareRecipeCondition;
import net.packages.flying_machines.sound.Sounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class flying_machines implements ModInitializer {
	public static final GameRules.Key<GameRules.BooleanRule> IS_ATMS_BREAKABLE = GameRuleRegistry.register("isAtmsBreakable", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
	public static final Identifier SECOND_FLOOR_LAB_CHEST_LOOT_TABLE_ID = new Identifier("flying_machines", "chests/2nd_floor_laboratory_chest");
	public static final Identifier TITANIUM_CHEST_LOOT_TABLE = new Identifier("flying_machines", "chests/titanium_chest");
	public static final Identifier ALUMINIUM_CHEST_LOOT_TABLE = new Identifier("flying_machines", "chests/aluminium_chest");
	public static final ScreenHandlerType<DylanScreenHandler> DYLAN_SCREEN_HANDLER;
	public static final ScreenHandlerType<ATMScreenHandler> ATM_SCREEN_HANDLER;
	public static final ScreenHandlerType<LockpickScreenHandler>LOCKPICK_SCREEN_HANDLER;
	public static final ScreenHandlerType<DylanSettingsScreenHandler> DYLAN_SETTINGS_SCREEN_HANDLER;
	public static boolean isCandlelightInstalled = FabricLoader.getInstance().isModLoaded("candlelight");
	static {
		DYLAN_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("flying_machines", "dylan_screen"), DylanScreenHandler::new);
		ATM_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("flying_machines", "atm_screen"), ATMScreenHandler::new);
		LOCKPICK_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("flying_machines", "lockpick_screen"), (ScreenHandlerRegistry.SimpleClientHandlerFactory<LockpickScreenHandler>) LockpickScreenHandler::new);
		DYLAN_SETTINGS_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier("flying_machines", "dylan_settings_screen"), DylanSettingsScreenHandler::new);
	}
	public static final RegistryKey<PlacedFeature> LARGE_TITANIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("flying_machines","ore_titanium_large"));
	public static final RegistryKey<PlacedFeature> SMALL_TITANIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("flying_machines","ore_titanium_small"));
	public static final RegistryKey<PlacedFeature> LARGE_ALUMINIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("flying_machines","ore_aluminium_large"));
	public static final RegistryKey<PlacedFeature> SMALL_ALUMINIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("flying_machines","ore_aluminium_small"));
	public static final RegistryKey<PlacedFeature> LARGE_LITHIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("flying_machines","ore_lithium_large"));
	public static final RegistryKey<PlacedFeature> SMALL_LITHIUM_ORE_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("flying_machines","ore_lithium_small"));
	public static final String MOD_ID = "flying_machines";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
			DebugLockCommand.register(dispatcher);
		});
		SpecificItemRemovalPacket.register();
		ServerWorldEvents.LOAD.register((world, server) -> {
			MinecraftServer minecraftServer = server.getServer();
			PlayerCardHandler.initialize(minecraftServer, world.getOverworld());
		});
		GeckoLib.initialize();
		ModEventHandler.initialize();
		ResourceConditions.register(new Identifier(MOD_ID, "custom_condition"), BeefTartareRecipeCondition::shouldLoad);
		ItemRemovalPacket.register();
		CardGivenPacket.register();
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
	public static void giveItemToPlayer(ClientPlayerEntity player, ItemStack item, int amount) {
		ItemStack stack = new ItemStack(item.getItem(), amount);
		player.getInventory().insertStack(stack);
		System.out.println("Item " + item + ", amount " + amount);
	}

	private void onPlayerTick(ServerWorld serverWorld) {
		for (PlayerEntity player : serverWorld.getPlayers()) {
			ItemStack mainHandItem = player.getStackInHand(Hand.MAIN_HAND);
			if (mainHandItem.getItem() instanceof VItems) {
				int totalCurrency = calculateTotalCurrency(player);
				displayActionBar(player, totalCurrency);
			}
		}
	}

	private int calculateTotalCurrency(PlayerEntity player) {
		int total = 0;
		for (ItemStack stack : player.getInventory().main) {
			if (stack.getItem() instanceof VItems) {
				total += ((VItems) stack.getItem()).getValue() * stack.getCount();
			}
		}
		return total;
	}

	private void displayActionBar(PlayerEntity player, int totalCurrency) {
		Text whiteText = Text.translatable("gui.totalV.Total").formatted(Formatting.GRAY);
		Text afterVText = Text.translatable("gui.totalV.aferV").formatted(Formatting.GRAY);
		Text lightBlueText = Text.translatable("gui.totalV.V").formatted(Formatting.LIGHT_PURPLE);
		Text totalCurrencyText = Text.literal(String.valueOf(totalCurrency)).formatted(Formatting.AQUA);
		Text message = Text.empty().append(whiteText).append(lightBlueText).append(afterVText).append(totalCurrencyText);
		player.sendMessage(message, true);
	}
}
