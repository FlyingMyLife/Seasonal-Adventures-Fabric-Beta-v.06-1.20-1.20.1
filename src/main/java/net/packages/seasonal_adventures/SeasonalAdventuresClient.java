package net.packages.seasonal_adventures;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.packages.seasonal_adventures.block.entity.BlockEntities;
import net.packages.seasonal_adventures.block.entity.client.GuidingSkinthBlockRenderer;
import net.packages.seasonal_adventures.block.entity.client.LockedChestLvLCopperBlockRenderer;
import net.packages.seasonal_adventures.block.entity.client.LockedChestLvLIronBlockRenderer;
import net.packages.seasonal_adventures.entity.Entities;
import net.packages.seasonal_adventures.entity.client.*;
import net.packages.seasonal_adventures.entity.client.legacy.DylanModel;
import net.packages.seasonal_adventures.entity.client.legacy.DylanRenderer;
import net.packages.seasonal_adventures.gui.handler.DylanScreenHandler;
import net.packages.seasonal_adventures.gui.handler.DylanSettingsScreenHandler;
import net.packages.seasonal_adventures.gui.screen.ATMScreen;
import net.packages.seasonal_adventures.gui.screen.DylanScreen;
import net.packages.seasonal_adventures.gui.screen.DylanSettingsScreen;
import net.packages.seasonal_adventures.gui.screen.LockpickScreen;
import net.packages.seasonal_adventures.network.client.ConfigSyncPacket;
import net.packages.seasonal_adventures.particle.Particles;
import net.packages.seasonal_adventures.particle.client.FadingParticle;

import static net.packages.seasonal_adventures.SeasonalAdventures.*;

@Environment(EnvType.CLIENT)
public class SeasonalAdventuresClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(LOCKPICK_SCREEN_HANDLER, LockpickScreen::new);
        ScreenRegistry.register(DYLAN_SCREEN_HANDLER, DylanScreen::new);
        ScreenRegistry.register(ATM_SCREEN_HANDLER, ATMScreen::new);
        ScreenRegistry.register(DYLAN_SETTINGS_SCREEN_HANDLER, (DylanSettingsScreenHandler handler, PlayerInventory inventory, Text title) -> new DylanSettingsScreen(handler, inventory, title, new DylanScreen(new DylanScreenHandler(0, inventory), inventory, Text.empty())));

        EntityRendererRegistry.register(Entities.DYLAN, DylanRenderer::new);
        EntityRendererRegistry.register(Entities.ATM, ATMRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModelLayers.DYLAN, DylanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.ATM, ATMModel::getTexturedModelData);
        BlockEntityRendererFactories.register(BlockEntities.LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY, LockedChestLvLCopperBlockRenderer::new);
        BlockEntityRendererFactories.register(BlockEntities.LOCKED_CHEST_LVL_IRON_BLOCK_ENTITY, LockedChestLvLIronBlockRenderer::new);
        BlockEntityRendererFactories.register(BlockEntities.GUIDING_SKINTH_BLOCK_ENTITY, GuidingSkinthBlockRenderer::new);

        ParticleFactoryRegistry.getInstance().register(Particles.SKINTH_PARTICLE, FadingParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(Particles.SKINTH_OF_DREAMS_PARTICLE, FadingParticle.Factory::new);

        ConfigSyncPacket.registerClientPacket();
    }

}