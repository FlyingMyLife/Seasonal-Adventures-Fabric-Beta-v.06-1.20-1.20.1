package net.packages.seasonal_adventures.gui;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.gui.handler.ATMScreenHandler;
import net.packages.seasonal_adventures.gui.handler.DylanScreenHandler;
import net.packages.seasonal_adventures.gui.handler.DylanSettingsScreenHandler;
import net.packages.seasonal_adventures.gui.handler.LockpickScreenHandler;
import net.packages.seasonal_adventures.gui.screen.ingame.ATMScreen;
import net.packages.seasonal_adventures.gui.screen.ingame.DylanScreen;
import net.packages.seasonal_adventures.gui.screen.ingame.DylanSettingsScreen;
import net.packages.seasonal_adventures.gui.screen.ingame.UnlockingScreen;

public class SAScreenHandlers {
    public static final ScreenHandlerType<ATMScreenHandler> ATM_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(SeasonalAdventures.MOD_ID, "atm_screen"),
            new ScreenHandlerType<>(ATMScreenHandler::new, FeatureSet.empty())
    );
    public static final ScreenHandlerType<LockpickScreenHandler> LOCKPICK_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(SeasonalAdventures.MOD_ID, "lockpick_screen"),
            new ScreenHandlerType<>(LockpickScreenHandler::new, FeatureSet.empty())
    );
    public static final ScreenHandlerType<DylanScreenHandler> DYLAN_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(SeasonalAdventures.MOD_ID, "dylan_screen"),
            new ScreenHandlerType<>(DylanScreenHandler::new, FeatureSet.empty())
    );
    public static final ScreenHandlerType<DylanSettingsScreenHandler> DYLAN_SETTINGS_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(SeasonalAdventures.MOD_ID, "dylan_settings_screen"),
            new ScreenHandlerType<>(DylanSettingsScreenHandler::new, FeatureSet.empty())
    );

    public static void registerHandledScreens() {
        HandledScreens.register(ATM_SCREEN_HANDLER, ATMScreen::new);
        HandledScreens.register(LOCKPICK_SCREEN_HANDLER, UnlockingScreen::new);
        HandledScreens.register(DYLAN_SCREEN_HANDLER, DylanScreen::new);
        HandledScreens.register(DYLAN_SETTINGS_SCREEN_HANDLER, DylanSettingsScreen::new);
    }
}
