package net.packages.seasonal_adventures.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.packages.seasonal_adventures.command.ai_config.AiPropertiesCommand;

public class SACommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            AiPropertiesCommand.register(dispatcher);
        });
    }
}
