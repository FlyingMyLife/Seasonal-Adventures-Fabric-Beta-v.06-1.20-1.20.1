package net.flyingmylife.seasonal_adventures;

import net.fabricmc.api.ModInitializer;
import net.flyingmylife.seasonal_adventures.block.SABlocks;
import net.flyingmylife.seasonal_adventures.block.entity.SABlockEntities;
import net.flyingmylife.seasonal_adventures.command.SACommands;
import net.flyingmylife.seasonal_adventures.entity.SAEntities;
import net.flyingmylife.seasonal_adventures.event.SAEvents;
import net.flyingmylife.seasonal_adventures.gui.SAScreenHandlers;
import net.flyingmylife.seasonal_adventures.item.SAItemGroups;
import net.flyingmylife.seasonal_adventures.item.SAItems;
import net.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes;
import net.flyingmylife.seasonal_adventures.particle.SAParticles;
import net.flyingmylife.seasonal_adventures.sound.SASounds;
import net.flyingmylife.seasonal_adventures.world.generator.SAWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SA implements ModInitializer {
	public static final String MOD_ID = "seasonal_adventures";
	public static final Logger LOGGER = LoggerFactory.getLogger("Seasonal Adventures");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Modding!");
		SASounds.registerSoundEvents();
		SAScreenHandlers.registerScreenHandlerTypes();
		SAWorldGeneration.registerBiomeModifications();
		SAEvents.registerEvents();
		SAPayloadTypes.S2C.registerPayloadTypes();
		SAPayloadTypes.C2S.registerPayloadTypes();
		SAPayloadTypes.C2S.registerGlobalReceivers();
		SACommands.registerCommands();
		SAParticles.registerParticles();
		SABlocks.registerBlocks();
		SAItems.registerItems();
		SAItemGroups.registerItemGroups();
		SABlockEntities.registerEntities();
		SAEntities.registerEntityAttributes();
	}
}