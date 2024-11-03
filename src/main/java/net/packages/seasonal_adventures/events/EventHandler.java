package net.packages.seasonal_adventures.events;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class EventHandler {
    public static void initialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                JDBCardHandler.initialize(server, world);
            }
        });
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            HudRenderCallback.EVENT.register(new HudRenderCallbackHandler());
        }
    }
}