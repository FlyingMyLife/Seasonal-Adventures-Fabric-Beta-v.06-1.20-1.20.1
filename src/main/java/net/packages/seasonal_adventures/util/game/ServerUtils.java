package net.packages.seasonal_adventures.util.game;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.Structure;
import net.packages.seasonal_adventures.SeasonalAdventures;

import java.nio.file.Path;

public class ServerUtils {
    public static Path getWorldSavePath(MinecraftServer server) {
        return server.getSavePath(WorldSavePath.ROOT).toAbsolutePath();
    }
    public static void placeStructure(ServerWorld world, RegistryEntry.Reference<Structure> structure, BlockPos pos) {
        Structure structure2 = structure.value();
        ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
        StructureStart structureStart = structure2.createStructureStart(world.getRegistryManager(), chunkGenerator, chunkGenerator.getBiomeSource(), world.getChunkManager().getNoiseConfig(), world.getStructureTemplateManager(), world.getSeed(), new ChunkPos(pos), 0, world, (biome) -> {
            return true;
        });
        if (!structureStart.hasChildren()) {
            SeasonalAdventures.LOGGER.error("Failed to place island of dreams");
        } else {
            BlockBox blockBox = structureStart.getBoundingBox();
            ChunkPos chunkPos = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMinX()), ChunkSectionPos.getSectionCoord(blockBox.getMinZ()));
            ChunkPos chunkPos2 = new ChunkPos(ChunkSectionPos.getSectionCoord(blockBox.getMaxX()), ChunkSectionPos.getSectionCoord(blockBox.getMaxZ()));
            ChunkPos.stream(chunkPos, chunkPos2).forEach((chunkPosx) -> {
                structureStart.place(world, world.getStructureAccessor(), chunkGenerator, world.getRandom(), new BlockBox(chunkPosx.getStartX(), world.getBottomY(), chunkPosx.getStartZ(), chunkPosx.getEndX(), world.getTopY(), chunkPosx.getEndZ()), chunkPosx);
            });
        }
    }
}
