package net.packages.seasonal_adventures.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class WorldUtils {

    public static ServerWorld getOverworld(MinecraftServer server) {
        return server.getWorld(World.OVERWORLD);
    }

}