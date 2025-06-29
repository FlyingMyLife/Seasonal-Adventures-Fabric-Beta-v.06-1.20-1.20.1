package net.flyingmylife.seasonal_adventures.event;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.flyingmylife.seasonal_adventures.SA;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.util.Identifier;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.level.storage.LevelStorage;

import java.io.File;
import java.util.Objects;

public class SAEvents {
    public static void registerEvents() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            File locationSupplierDir = new File(FabricLoader.getInstance().getGameDir().toString() + "/teleportLocations");
            HudRenderCallback.EVENT.register(new HudRenderCallbackHandler());
            ClientLifecycleEvents.CLIENT_STARTED.register((client -> {
                if (!locationSupplierDir.exists()){
                    if (locationSupplierDir.mkdirs()) {
                        SA.LOGGER.info("Created teleportLocations dir");
                    } else SA.LOGGER.error("Failed to create teleportLocations dir");
                }
            }));
            ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
                if (!Objects.requireNonNull(client.getServer()).isSingleplayer()) {
                    String serverAddress = Objects.requireNonNull(handler.getServerInfo()).address;
                    File serverCache = new File(locationSupplierDir + "/.cache/" + serverAddress);
                    if (!serverCache.exists()){
                        if (serverCache.mkdirs()) {
                            SA.LOGGER.info("Created cache dir for server: {} ", serverAddress);
                        } else SA.LOGGER.error("Failed to create cache dir for server: {}", serverAddress);
                    }
                } else {
                    String worldName = client.getServer().getSaveProperties().getLevelName();
                    File serverCache = new File(locationSupplierDir + "/world/" + worldName);
                    if (!serverCache.exists()){
                        if (serverCache.mkdirs()) {
                            SA.LOGGER.info("Created world config dir, world: {} ", worldName);
                        } else SA.LOGGER.error("Failed to create world config dir, world: {} ", worldName);
                    }
                }
            }));
        }
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
        });

    }
}