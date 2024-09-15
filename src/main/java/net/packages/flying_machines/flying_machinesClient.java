package net.packages.flying_machines;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.packages.flying_machines.block.entity.ModBlockEntities;
import net.packages.flying_machines.block.entity.client.AnimatedBlockRenderer;
import net.packages.flying_machines.entity.Entities;
import net.packages.flying_machines.entity.client.*;
import net.packages.flying_machines.gui.ATMScreen;
import net.packages.flying_machines.gui.DylanScreen;
import net.packages.flying_machines.gui.DylanSettingsScreen;
import static net.packages.flying_machines.flying_machines.*;


public class flying_machinesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(DYLAN_SCREEN_HANDLER, DylanScreen::new);
        ScreenRegistry.register(ATM_SCREEN_HANDLER, ATMScreen::new);
        ScreenRegistry.register(DYLAN_SETTINGS_SCREEN_HANDLER, DylanSettingsScreen::new);
        EntityRendererRegistry.register(Entities.DYLAN, DylanRenderer::new);
        EntityRendererRegistry.register(Entities.ATM, ATMRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.DYLAN, DylanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.ATM, ATMModel::getTexturedModelData);
        BlockEntityRendererFactories.register(ModBlockEntities.ANIMATED_BLOCK_ENTITY, AnimatedBlockRenderer::new);
    }

}