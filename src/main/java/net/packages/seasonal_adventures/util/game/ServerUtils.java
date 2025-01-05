package net.packages.seasonal_adventures.util.game;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;
import net.packages.seasonal_adventures.SeasonalAdventures;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class ServerUtils {
    public static Path getWorldSavePath(MinecraftServer server) {
        return server.getSavePath(WorldSavePath.ROOT).toAbsolutePath();
    }
    public static void placeStructure(ServerWorld world, Identifier structureId, BlockPos pos) {
        StructureTemplateManager templateManager = world.getStructureTemplateManager();
        Optional<StructureTemplate> optionalTemplate = templateManager.getTemplate(structureId);
        if (optionalTemplate.isEmpty()) {
            SeasonalAdventures.LOGGER.error("Structure with ID {} not found!", structureId);
            return;
        }

        StructureTemplate structureTemplate = optionalTemplate.get();
        StructurePlacementData placementData = new StructurePlacementData();
        boolean success = structureTemplate.place(world, pos, pos, placementData, world.random, 2);
        if (success) {
            SeasonalAdventures.LOGGER.info("Successfully placed structure");
        } else {
            SeasonalAdventures.LOGGER.error("Failed to place structure");
        }
    }
}
