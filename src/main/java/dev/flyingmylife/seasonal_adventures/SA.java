package dev.flyingmylife.seasonal_adventures;

import net.fabricmc.api.ModInitializer;
import dev.flyingmylife.seasonal_adventures.block.SABlocks;
import dev.flyingmylife.seasonal_adventures.block.entity.SABlockEntities;
import dev.flyingmylife.seasonal_adventures.command.SACommands;
import dev.flyingmylife.seasonal_adventures.entity.SAEntities;
import dev.flyingmylife.seasonal_adventures.event.SAEvents;
import dev.flyingmylife.seasonal_adventures.item.SAItemGroups;
import dev.flyingmylife.seasonal_adventures.item.SAItems;
import dev.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes;
import dev.flyingmylife.seasonal_adventures.particle.SAParticles;
import dev.flyingmylife.seasonal_adventures.world.generator.SAWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SA implements ModInitializer {
	public static final String MOD_ID = "seasonal_adventures";
	public static final Logger LOGGER = LoggerFactory.getLogger("Seasonal Adventures");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Modding!");

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