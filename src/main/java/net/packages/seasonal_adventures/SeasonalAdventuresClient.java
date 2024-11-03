package net.packages.seasonal_adventures;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.packages.seasonal_adventures.block.entity.ModBlockEntities;
import net.packages.seasonal_adventures.block.entity.client.AnimatedBlockRenderer;
import net.packages.seasonal_adventures.block.entity.client.EnergyInjectorBlockRenderer;
import net.packages.seasonal_adventures.block.entity.client.LockedChestLvLCopperBlockRenderer;
import net.packages.seasonal_adventures.block.entity.client.LockedChestLvLIronBlockRenderer;
import net.packages.seasonal_adventures.entity.Entities;
import net.packages.seasonal_adventures.entity.client.*;
import net.packages.seasonal_adventures.gui.*;
import net.packages.seasonal_adventures.gui.handlers.LockpickScreenHandler;

import static net.packages.seasonal_adventures.SeasonalAdventures.*;

@Environment(EnvType.CLIENT)
public class SeasonalAdventuresClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(LOCKPICK_SCREEN_HANDLER, (LockpickScreenHandler handler, PlayerInventory inventory, Text title) -> new LockpickScreen(handler, inventory, title, 0));
        ScreenRegistry.register(DYLAN_SCREEN_HANDLER, DylanScreen::new);
        ScreenRegistry.register(ATM_SCREEN_HANDLER, ATMScreen::new);
        ScreenRegistry.register(DYLAN_SETTINGS_SCREEN_HANDLER, DylanSettingsScreen::new);
        EntityRendererRegistry.register(Entities.DYLAN, DylanRenderer::new);
        EntityRendererRegistry.register(Entities.ATM, ATMRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.DYLAN, DylanModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModelLayers.ATM, ATMModel::getTexturedModelData);
        BlockEntityRendererFactories.register(ModBlockEntities.ANIMATED_BLOCK_ENTITY, AnimatedBlockRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.ENERGY_INJECTOR_BLOCK_ENTITY, EnergyInjectorBlockRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.LOCKED_CHEST_LVL_COPPER_BLOCK_ENTITY, LockedChestLvLCopperBlockRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.LOCKED_CHEST_LVL_IRON_BLOCK_ENTITY, LockedChestLvLIronBlockRenderer::new);
    }

}