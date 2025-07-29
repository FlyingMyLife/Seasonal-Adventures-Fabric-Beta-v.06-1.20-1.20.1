package net.flyingmylife.seasonal_adventures;
import net.flyingmylife.seasonal_adventures.entity.client.layer.SARenderLayers;
import net.flyingmylife.seasonal_adventures.event.SAEvents;
import net.flyingmylife.seasonal_adventures.world.generator.noise.TimeInfectionLevelTemperatureMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.flyingmylife.seasonal_adventures.block.entity.SABlockEntities;
import net.flyingmylife.seasonal_adventures.config.ai.AiPropertiesManager;
import net.flyingmylife.seasonal_adventures.entity.SAEntities;
import net.flyingmylife.seasonal_adventures.gui.SAScreenHandlers;
import net.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes;
import net.flyingmylife.seasonal_adventures.particle.SAParticles;

@Environment(EnvType.CLIENT)
public class SAClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SAScreenHandlers.registerHandledScreens();
        SAEntities.registerEntityAttributes();
        SABlockEntities.registerEntityRenderFactories();
        SAParticles.registerParticleFactories();
        AiPropertiesManager.initialize();
        SAPayloadTypes.S2C.registerGlobalReceivers();
        SAEvents.registerClientEvents();
        SARenderLayers.registerLayers();
        SAEntities.registerEntityRenderers();
        TimeInfectionLevelTemperatureMap map = new TimeInfectionLevelTemperatureMap(132112421323321200L);
        map.generateTemperatureMap();
    }

}