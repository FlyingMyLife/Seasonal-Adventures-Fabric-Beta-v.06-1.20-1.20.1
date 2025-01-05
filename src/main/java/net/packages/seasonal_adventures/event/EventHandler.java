package net.packages.seasonal_adventures.event;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.util.game.ServerUtils;

import java.io.File;
import java.util.Objects;

public class EventHandler {
    public static void initialize() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            File locationSupplierDir = new File(FabricLoader.getInstance().getGameDir().toString() + "/teleportLocations");
            HudRenderCallback.EVENT.register(new HudRenderCallbackHandler());
            ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
                if (!locationSupplierDir.exists()){
                    if (locationSupplierDir.mkdirs()) {
                        SeasonalAdventures.LOGGER.info("Created teleportLocations dir");
                    } else SeasonalAdventures.LOGGER.error("Failed to create teleportLocations dir");
                }
            }));
            ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
                if (!Objects.requireNonNull(client.getServer()).isSingleplayer()) {
                    String serverAddress = Objects.requireNonNull(handler.getServerInfo()).address;
                    File serverCache = new File(locationSupplierDir + "/.cache/" + serverAddress);
                    if (!serverCache.exists()){
                        if (serverCache.mkdirs()) {
                            SeasonalAdventures.LOGGER.info("Created cache dir for server: {} ", serverAddress);
                        } else SeasonalAdventures.LOGGER.error("Failed to create cache dir for server: {}", serverAddress);
                    }
                } else {
                    String worldName = client.getServer().getSaveProperties().getLevelName();
                    File serverCache = new File(locationSupplierDir + "/world/" + worldName);
                    if (!serverCache.exists()){
                        if (serverCache.mkdirs()) {
                            SeasonalAdventures.LOGGER.info("Created world config dir, world: {} ", worldName);
                        } else SeasonalAdventures.LOGGER.error("Failed to create world config dir, world: {} ", worldName);
                    }
                }
            }));
        }
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
        });
    }
}