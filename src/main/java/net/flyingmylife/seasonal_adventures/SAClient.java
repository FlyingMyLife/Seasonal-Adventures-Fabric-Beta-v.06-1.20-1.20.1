package net.flyingmylife.seasonal_adventures;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.flyingmylife.seasonal_adventures.block.SABlocks;
import net.flyingmylife.seasonal_adventures.entity.client.layer.SARenderLayers;
import net.flyingmylife.seasonal_adventures.event.SAEvents;
import net.flyingmylife.seasonal_adventures.gui.data.EntityTrackingPool;
import net.flyingmylife.seasonal_adventures.gui.screen.in_game.DuckerScreen;
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
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class SAClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SAScreenHandlers.registerHandledScreens();
        SAEntities.registerEntityAttributes();
        SABlockEntities.registerEntityRenderFactories();
        SABlocks.registerBlockRenderLayers();
        SAParticles.registerParticleFactories();
        AiPropertiesManager.initialize();
        SAPayloadTypes.S2C.registerGlobalReceivers();
        SAEvents.registerClientEvents();
        SARenderLayers.registerLayers();
        SAEntities.registerEntityRenderers();
        EntityTrackingPool.init();
    }

}