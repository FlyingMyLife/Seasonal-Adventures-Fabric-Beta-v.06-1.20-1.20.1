package net.packages.seasonal_adventures.events;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.world.ServerWorld;

public class EventHandler {
    public static void initialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                JDBCardHandler.initialize(server, world);
            }
        });
    }
}