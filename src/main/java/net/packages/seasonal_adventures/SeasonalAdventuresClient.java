package net.packages.seasonal_adventures;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.packages.seasonal_adventures.block.entity.BlockEntities;
import net.packages.seasonal_adventures.block.entity.client.GuidingSkinthBlockRenderer;
import net.packages.seasonal_adventures.block.entity.client.LockedChestLvLCopperBlockRenderer;
import net.packages.seasonal_adventures.block.entity.client.LockedChestLvLIronBlockRenderer;
import net.packages.seasonal_adventures.config.ai.AiPropertiesLoader;
import net.packages.seasonal_adventures.entity.Entities;
import net.packages.seasonal_adventures.entity.client.*;
import net.packages.seasonal_adventures.gui.ScreenHandlers;
import net.packages.seasonal_adventures.gui.screen.ingame.ATMScreen;
import net.packages.seasonal_adventures.gui.screen.ingame.DylanScreen;
import net.packages.seasonal_adventures.gui.screen.ingame.DylanSettingsScreen;
import net.packages.seasonal_adventures.gui.screen.ingame.UnlockingScreen;
import net.packages.seasonal_adventures.network.client.ConfigSyncPacket;
import net.packages.seasonal_adventures.particle.Particles;
import net.packages.seasonal_adventures.particle.client.FadingParticle;

@Environment(EnvType.CLIENT)
public class SeasonalAdventuresClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ScreenHandlers.ATM_SCREEN_HANDLER, ATMScreen::new);
        HandledScreens.register(ScreenHandlers.LOCKPICK_SCREEN_HANDLER, UnlockingScreen::new);
        HandledScreens.register(ScreenHandlers.DYLAN_SCREEN_HANDLER, DylanScreen::new);
        HandledScreens.register(ScreenHandlers.DYLAN_SETTINGS_SCREEN_HANDLER, DylanSettingsScreen::new);

        EntityRendererRegistry.register(Entities.ATM, ATMRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModelLayers.ATM, ATMModel::getTexturedModelData);
        BlockEntityRendererFactories.register(BlockEntities.LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY, LockedChestLvLCopperBlockRenderer::new);
        BlockEntityRendererFactories.register(BlockEntities.LOCKED_CHEST_LVL_IRON_BLOCK_ENTITY, LockedChestLvLIronBlockRenderer::new);
        BlockEntityRendererFactories.register(BlockEntities.GUIDING_SKINTH_BLOCK_ENTITY, GuidingSkinthBlockRenderer::new);

        ParticleFactoryRegistry.getInstance().register(Particles.SKINTH_PARTICLE, FadingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Particles.SKINTH_OF_DREAMS_PARTICLE, FadingParticle.Factory::new);
        AiPropertiesLoader.initialize();
        ConfigSyncPacket.registerClientPacket();
    }

}