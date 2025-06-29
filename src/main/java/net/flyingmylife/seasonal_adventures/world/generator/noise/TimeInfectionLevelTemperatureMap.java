package net.flyingmylife.seasonal_adventures.world.generator.noise;

import net.flyingmylife.seasonal_adventures.SA;
import net.fabricmc.loader.api.FabricLoader;
import org.joml.SimplexNoise;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class TimeInfectionLevelTemperatureMap {
    private final long seed;
    private static final int SIZE = 512;
    private static final int SPAWN_RADIUS = 16;
    private static final int FULL_INFECTION_RADIUS = 1024;
    private static final int HARD_ZONE_START = 700;

    public TimeInfectionLevelTemperatureMap(long seed) {
        this.seed = seed;
    }

    public int getTemperature(int chunkX, int chunkZ) {
        double distance = Math.sqrt(chunkX * chunkX + chunkZ * chunkZ);
        Random random = new Random(seed + chunkX * 341873128712L + chunkZ * 132897987541L);
        double noise = SimplexNoise.noise((float) (chunkX * 0.1), (float) (chunkZ * 0.1)) * 0.5 + 0.5;

        double infectionFactor = Math.log1p((distance - SPAWN_RADIUS) / (FULL_INFECTION_RADIUS - SPAWN_RADIUS) * 10);
        int baseLevel = (int) (infectionFactor * 12);
        int noiseAdjustment = (int) ((noise - 0.5) * 6);
        int infectionLevel = baseLevel + noiseAdjustment;

        // Ensure rare 8-16 in normal zone before 700
        if (distance < HARD_ZONE_START && random.nextDouble() < 0.05) {
            infectionLevel = Math.max(infectionLevel, 8 + random.nextInt(9));
        }

        // Hard Zone (700+ chunks) - Black area
        if (distance >= HARD_ZONE_START) {
            infectionLevel = 16;
        }

        return Math.max(1, Math.min(16, infectionLevel));
    }

    public void generateTemperatureMap() {
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < SIZE; x++) {
            for (int z = 0; z < SIZE; z++) {
                int temp = getTemperature(x - SIZE / 2, z - SIZE / 2);
                int colorValue = Math.max(0, Math.min(255, (int) (255 - ((temp - 1) / 15.0) * 255)));
                Color color = new Color(colorValue, colorValue, colorValue);
                image.setRGB(x, z, color.getRGB());
            }
        }

        try {
            File output = new File(FabricLoader.getInstance().getGameDir().toString() + "\\temp_map\\time_infection_levels_9.png");
            output.mkdirs();
            ImageIO.write(image, "png", output);
            System.out.println("Saved: " + output.getAbsolutePath());
        } catch (IOException e) {
            SA.LOGGER.error("Failed to save temp map", e);
        }
    }
}
