package net.packages.seasonal_adventures.command.ai_config;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.network.client.SecretKeyUpdatePacket;

public class AiPropertiesCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(((CommandManager.literal("ai").then(CommandManager.literal("setKey")
                        .then(CommandManager.argument("key", StringArgumentType.string())
                                .executes(context -> {
                                            String key = StringArgumentType.getString(context, "key");
                                            if (context.getSource().isExecutedByPlayer()) {
                                                ServerPlayerEntity player = context.getSource().getPlayer();
                                                assert player != null;
                                                player.sendMessage(Text.literal("Установка ключа..."));
                                                SecretKeyUpdatePacket.requestSecretKeyUpdate(player, key);
                                            } else {
                                                SeasonalAdventures.LOGGER.info("Player not found, command ignored");
                                            }
                                            return 1;
                                        }
                                )
                        )
                )
        )));
    }
}
