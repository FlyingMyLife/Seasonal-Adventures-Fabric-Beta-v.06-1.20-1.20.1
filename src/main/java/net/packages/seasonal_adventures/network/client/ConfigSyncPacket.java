package net.packages.seasonal_adventures.network.client;

import com.google.gson.Gson;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.config.object.ConfigObject;
import net.packages.seasonal_adventures.config.ConfigReader;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ConfigSyncPacket {
    private static final Identifier REQUEST_ID = Identifier.of(SeasonalAdventures.MOD_ID, "request_config_sync_packet");
    private static final Identifier SEND_ID = Identifier.of(SeasonalAdventures.MOD_ID, "send_config_sync_packet");
    private static final Gson GSON = new Gson();

    private static CompletableFuture<ConfigObject> configFuture;

    public static void registerClientPacket() {
        ClientPlayNetworking.registerGlobalReceiver(SEND_ID, (client, handler, buf, responseSender) -> {
            String configData = buf.readString();
            client.execute(() -> {
                try {
                    ConfigObject config = GSON.fromJson(configData, ConfigObject.class);
                    SeasonalAdventures.LOGGER.info("Received config");

                    if (configFuture != null) {
                        configFuture.complete(config);
                        configFuture = null;
                    }
                } catch (Exception e) {
                    SeasonalAdventures.LOGGER.error("Failed to parse config data: ", e);
                    if (configFuture != null) {
                        configFuture.completeExceptionally(e);
                        configFuture = null;
                    }
                }
            });
        });
    }

    public static void registerServerPacket() {
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                SeasonalAdventures.LOGGER.info("Got config request, sending...");
                try {
                    ConfigObject configData = ConfigReader.readConfig();
                    String jsonConfig = GSON.toJson(configData);

                    PacketByteBuf responseBuf = PacketByteBufs.create();
                    responseBuf.writeString(jsonConfig);

                    ServerPlayNetworking.send(player, SEND_ID, responseBuf);
                    SeasonalAdventures.LOGGER.info("ConfigObject sent to player: {}", player.getName().getString());
                } catch (Exception e) {
                    SeasonalAdventures.LOGGER.error("Failed to process config request: ", e);
                }
            });
        });
    }


    public static void requestConfigFromServer() {
        SeasonalAdventures.LOGGER.info("Requested configs from server, awaiting response...");
        ClientPlayNetworking.send(REQUEST_ID, PacketByteBufs.empty());
    }
    public static void getConfigFromServerAsync(Consumer<ConfigObject> callback) {
        configFuture = new CompletableFuture<>();
        requestConfigFromServer();

        configFuture.whenComplete((config, throwable) -> {
            if (throwable != null) {
                SeasonalAdventures.LOGGER.error("Failed to retrieve config from server", throwable);
                callback.accept(null);
            } else {
                SeasonalAdventures.LOGGER.info("Successfully retrieved config");
                callback.accept(config);
            }
        });
    }
}
