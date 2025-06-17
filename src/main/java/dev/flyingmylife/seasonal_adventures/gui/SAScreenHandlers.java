package dev.flyingmylife.seasonal_adventures.gui;

import dev.flyingmylife.seasonal_adventures.SA;
import dev.flyingmylife.seasonal_adventures.gui.handler.DuckerScreenHandler;
import dev.flyingmylife.seasonal_adventures.gui.screen.ingame.DuckerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import dev.flyingmylife.seasonal_adventures.gui.handler.ATMScreenHandler;
import dev.flyingmylife.seasonal_adventures.gui.handler.DylanSettingsScreenHandler;
import dev.flyingmylife.seasonal_adventures.gui.handler.LockpickScreenHandler;
import dev.flyingmylife.seasonal_adventures.gui.screen.ingame.ATMScreen;
import dev.flyingmylife.seasonal_adventures.gui.screen.ingame.DylanSettingsScreen;
import dev.flyingmylife.seasonal_adventures.gui.screen.ingame.UnlockingScreen;

public class SAScreenHandlers {
    public static final ScreenHandlerType<ATMScreenHandler> ATM_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(SA.MOD_ID, "atm_screen"),
            new ScreenHandlerType<>(ATMScreenHandler::new, FeatureFlags.VANILLA_FEATURES)
    );
    public static final ScreenHandlerType<LockpickScreenHandler> LOCKPICK_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(SA.MOD_ID, "lockpick_screen"),
            new ScreenHandlerType<>(LockpickScreenHandler::new, FeatureFlags.VANILLA_FEATURES)
    );
    public static final ScreenHandlerType<DylanSettingsScreenHandler> DYLAN_SETTINGS_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(SA.MOD_ID, "dylan_settings_screen"),
            new ScreenHandlerType<>(DylanSettingsScreenHandler::new, FeatureFlags.VANILLA_FEATURES)
    );
    public static final ScreenHandlerType<DuckerScreenHandler> DUCKER_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            "ducker_screen",
            new ScreenHandlerType<>(DuckerScreenHandler::new, FeatureFlags.VANILLA_FEATURES)
    );

    public static void registerHandledScreens() {
        HandledScreens.register(ATM_SCREEN_HANDLER, ATMScreen::new);
        HandledScreens.register(DUCKER_SCREEN_HANDLER, DuckerScreen::new);
        HandledScreens.register(LOCKPICK_SCREEN_HANDLER, UnlockingScreen::new);
        HandledScreens.register(DYLAN_SETTINGS_SCREEN_HANDLER, DylanSettingsScreen::new);
    }
}
