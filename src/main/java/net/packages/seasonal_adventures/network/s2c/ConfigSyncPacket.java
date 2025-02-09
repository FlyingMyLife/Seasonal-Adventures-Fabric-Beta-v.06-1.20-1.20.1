package net.packages.seasonal_adventures.network.s2c;

import com.google.gson.Gson;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.config.server.ServerConfig;
import net.packages.seasonal_adventures.network.SAPayloadTypes;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ConfigSyncPacket {
    private static final Gson GSON = new Gson();
    private static CompletableFuture<ServerConfig> configFuture;

    public static void registerS2C(SAPayloadTypes.S2C.ConfigSyncPayload payload, ClientPlayNetworking.Context context) {
            String configData = payload.configData();

        try {
            ServerConfig config = GSON.fromJson(configData, ServerConfig.class);
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
    }

    public static void registerServerPacket() {
        SeasonalAdventures.LOGGER.info("Got config request, sending...");
        try {
            ServerConfig configData = ServerConfig.HANDLER.readConfig();
            String jsonConfig = GSON.toJson(configData);

            PacketByteBuf responseBuf = PacketByteBufs.create();
            responseBuf.writeString(jsonConfig);

            //ServerPlayNetworking.send(player, SEND_ID, responseBuf);
            //SeasonalAdventures.LOGGER.info("ServerConfig sent to player: {}", player);
        } catch (Exception e) {
            SeasonalAdventures.LOGGER.error("Failed to process config request: ", e);
        }
    }


    public static void requestConfigFromServer() {
        SeasonalAdventures.LOGGER.info("Requested configs from server, awaiting response...");
        //ClientPlayNetworking.send(SAPayloadTypes.S2C.ConfigSyncPayload.ID, new SAPayloadTypes.S2C.ConfigSyncPayload());
    }
    public static void getConfigFromServerAsync(Consumer<ServerConfig> callback) {
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
