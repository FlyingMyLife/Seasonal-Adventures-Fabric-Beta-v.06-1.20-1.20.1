package net.flyingmylife.seasonal_adventures.command.ai_config;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.flyingmylife.seasonal_adventures.config.ai.AiPropertiesManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class AiPropertiesCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register((ClientCommandManager.literal("ai").then(ClientCommandManager.literal("setKey")
                .then(ClientCommandManager.argument("key", StringArgumentType.string())
                        .executes(context -> {
                                    String key = StringArgumentType.getString(context, "key");
                                    MinecraftClient client = context.getSource().getClient();
                                    ClientPlayerEntity player = context.getSource().getPlayer();
                                    player.sendMessage(Text.translatable("message.seasonal_adventures.ai.key.update", key), false);
                                        int code = AiPropertiesManager.checkKey(key);
                                        if (AiPropertiesManager.checkKey(key) == 0) {
                                            client.player.sendMessage(Text.translatable("message.seasonal_adventures.ai.key.confirmed").formatted(Formatting.GREEN), false);
                                            AiPropertiesManager.saveSecretKey(key);
                                        } else if (code == -1){
                                            player.sendMessage(Text.translatable("message.seasonal_adventures.ai.key.network_error").formatted(Formatting.RED), false);
                                        } else {
                                            player.sendMessage(Text.translatable("message.seasonal_adventures.ai.key.invalid", code).formatted(Formatting.RED), false);
                                        }
                                        return 1;
                                }
                        )
                )
        )));
    }
}
