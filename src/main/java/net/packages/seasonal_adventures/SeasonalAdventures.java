package net.packages.seasonal_adventures;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.packages.seasonal_adventures.block.SABlocks;
import net.packages.seasonal_adventures.block.entity.SABlockEntities;
import net.packages.seasonal_adventures.command.SACommands;
import net.packages.seasonal_adventures.entity.SAEntities;
import net.packages.seasonal_adventures.event.SAEvents;
import net.packages.seasonal_adventures.gui.screen.ConfigScreen;
import net.packages.seasonal_adventures.item.SAItemGroups;
import net.packages.seasonal_adventures.item.SAItems;
import net.packages.seasonal_adventures.network.SAPayloadTypes;
import net.packages.seasonal_adventures.particle.SAParticles;
import net.packages.seasonal_adventures.sound.SASounds;
import net.packages.seasonal_adventures.world.generator.SAWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeasonalAdventures implements ModInitializer {
	public static final String MOD_ID = "seasonal_adventures";
	public static final Logger LOGGER = LoggerFactory.getLogger("Seasonal Adventures");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Modding!");
		MidnightConfig.init(MOD_ID, ConfigScreen.class);

		SAPayloadTypes.S2C.registerPayloadTypes();
		SAWorldGeneration.registerBiomeModifications();
		SAEvents.registerEvents();
		SAPayloadTypes.C2S.registerPayloadTypes();
		SAPayloadTypes.C2S.registerPayloadTypes();
		SACommands.registerCommands();
		SAParticles.registerParticles();
		SABlockEntities.registerEntities();
		SASounds.registerSounds();
		SAItemGroups.registerItemGroups();
		SAItems.registerItems();
		SABlocks.registerBlocks();
		SAEntities.registerEntityAttributes();
	}
}