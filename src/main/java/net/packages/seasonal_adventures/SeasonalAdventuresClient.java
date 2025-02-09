package net.packages.seasonal_adventures;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.packages.seasonal_adventures.block.entity.SABlockEntities;
import net.packages.seasonal_adventures.config.ai.AiPropertiesManager;
import net.packages.seasonal_adventures.entity.SAEntities;
import net.packages.seasonal_adventures.gui.SAScreenHandlers;
import net.packages.seasonal_adventures.network.SAPayloadTypes;
import net.packages.seasonal_adventures.particle.SAParticles;

@Environment(EnvType.CLIENT)
public class SeasonalAdventuresClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SAScreenHandlers.registerHandledScreens();
        SAEntities.registerEntityAttributes();
        SABlockEntities.registerEntityRenderFactories();
        SAParticles.registerParticleFactories();
        AiPropertiesManager.initialize();
        SAPayloadTypes.S2C.registerGlobalReceivers();
    }

}