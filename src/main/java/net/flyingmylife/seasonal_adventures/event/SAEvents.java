package net.flyingmylife.seasonal_adventures.event;

import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.flyingmylife.seasonal_adventures.event.custom.SAClientLifeCycleEvents;
import net.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes;
import net.flyingmylife.seasonal_adventures.world.dimension.Dimensions;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;

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