package dev.flyingmylife.seasonal_adventures;
import dev.flyingmylife.seasonal_adventures.entity.client.layer.SARenderLayers;
import dev.flyingmylife.seasonal_adventures.world.generator.noise.TimeInfectionLevelTemperatureMap;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import dev.flyingmylife.seasonal_adventures.block.entity.SABlockEntities;
import dev.flyingmylife.seasonal_adventures.config.ai.AiPropertiesManager;
import dev.flyingmylife.seasonal_adventures.entity.SAEntities;
import dev.flyingmylife.seasonal_adventures.gui.SAScreenHandlers;
import dev.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes;
import dev.flyingmylife.seasonal_adventures.particle.SAParticles;

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

        SARenderLayers.registerLayers();
        SAEntities.registerEntityRenderers();
        TimeInfectionLevelTemperatureMap map = new TimeInfectionLevelTemperatureMap(132112421323321200L);
        map.generateTemperatureMap();
    }

}