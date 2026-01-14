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
import net.flyingmylife.seasonal_adventures.util.SAServices;
import net.flyingmylife.seasonal_adventures.world.generator.SAWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SA implements ModInitializer {
	public static final String MOD_ID = "seasonal_adventures";
	public static final Logger LOGGER = LoggerFactory.getLogger("Seasonal Adventures");
    public static final boolean DEV_ENVIRONMENT = true;

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Modding!");
		SAItems.registerItems();
		SAEvents.registerEvents();
		SABlocks.registerBlocks();
		SACommands.registerCommands();
        SAServices.registerServices();
		SASounds.registerSoundEvents();
		SAParticles.registerParticles();
        SAItemGroups.registerItemGroups();
        SABlockEntities.registerEntities();
        SAEntities.registerEntityAttributes();
        SAPayloadTypes.S2C.registerPayloadTypes();
        SAPayloadTypes.C2S.registerPayloadTypes();
        SAPayloadTypes.C2S.registerGlobalReceivers();
        SAScreenHandlers.registerScreenHandlerTypes();
        SAWorldGeneration.registerBiomeModifications();
	}
}