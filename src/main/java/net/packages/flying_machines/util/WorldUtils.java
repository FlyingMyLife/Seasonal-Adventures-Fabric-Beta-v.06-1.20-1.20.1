package net.packages.flying_machines.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class WorldUtils {

    public static ServerWorld getOverworld(MinecraftServer server) {
        return server.getWorld(World.OVERWORLD);
    }

    public static ServerWorld getNether(MinecraftServer server) {
        return server.getWorld(World.NETHER);
    }

    public static ServerWorld getEnd(MinecraftServer server) {
        return server.getWorld(World.END);
    }
}