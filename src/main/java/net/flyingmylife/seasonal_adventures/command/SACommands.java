package net.flyingmylife.seasonal_adventures.command;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.flyingmylife.seasonal_adventures.command.ai_config.AiPropertiesCommand;

public class SACommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
        });
    }

    public static void registerClientCommands() {
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            AiPropertiesCommand.register(dispatcher);
        }));
    }
}
