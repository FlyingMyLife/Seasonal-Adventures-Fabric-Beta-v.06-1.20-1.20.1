package net.flyingmylife.seasonal_adventures.util.game;

import net.flyingmylife.seasonal_adventures.SA;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockPos;

import java.nio.file.Path;
import java.util.Optional;

public class ServerUtils {
    public static Path getWorldSavePath(MinecraftServer server) {
        return server.getSavePath(WorldSavePath.ROOT).toAbsolutePath();
    }
    public static void placeStructure(ServerWorld world, Identifier structureId, BlockPos pos) {
        StructureTemplateManager templateManager = world.getStructureTemplateManager();
        Optional<StructureTemplate> optionalTemplate = templateManager.getTemplate(structureId);
        if (optionalTemplate.isEmpty()) {
            SA.LOGGER.error("Structure with ID {} not found!", structureId);
            return;
        }

        StructureTemplate structureTemplate = optionalTemplate.get();
        StructurePlacementData placementData = new StructurePlacementData();
        boolean success = structureTemplate.place(world, pos, pos, placementData, world.random, 2);
        if (success) {
            SA.LOGGER.info("Successfully placed structure");
        } else {
            SA.LOGGER.error("Failed to place structure");
        }
    }
}
