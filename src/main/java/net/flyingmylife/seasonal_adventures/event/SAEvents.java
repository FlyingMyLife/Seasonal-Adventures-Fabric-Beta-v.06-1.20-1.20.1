package net.flyingmylife.seasonal_adventures.event;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.flyingmylife.seasonal_adventures.event.custom.SAClientLifeCycleEvents;
import net.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes;
import net.minecraft.world.World;

import java.util.Objects;

public class SAEvents {
    public static void registerEvents() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            sender.sendPacket(SAPayloadTypes.S2C.InitializeClientDataPayload.create(Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getSeed()));
        });
    }

    public static void registerClientEvents() {
        SAClientLifeCycleEvents.registerClientStartUpEvents();
        SAClientLifeCycleEvents.registerServerConnectionEvents();
    }
}