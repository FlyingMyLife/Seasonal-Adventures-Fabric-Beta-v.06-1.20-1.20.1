package net.packages.seasonal_adventures.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.config.object.ConfigObject;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class ConfigBuilder {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static String configPath = FabricLoader.getInstance().getConfigDir().toString() + "\\seasonal_adventures.json";
    public static ConfigObject defaultConfig = new ConfigObject(false, false, false);

    public static void initializeConfig() {
        initializeDefaultConfig();
        if (!isValidConfig(configPath)) {
            SeasonalAdventures.LOGGER.info(FabricLoader.getInstance().getConfigDir().toString());
            try (FileWriter writer = new FileWriter(configPath)) {
                gson.toJson(defaultConfig, writer);
                SeasonalAdventures.LOGGER.info("Default config file created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeConfig(ConfigObject config) {
        try (FileWriter writer = new FileWriter(configPath)) {
            gson.toJson(config, writer);
            SeasonalAdventures.LOGGER.info("Updated config file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ConfigObject readConfig() {
        if (!isValidConfig(configPath)) {
            SeasonalAdventures.LOGGER.error("Failed to read config file, using default settings...");
            initializeConfig();
            return defaultConfig;
        } else {
            try (Reader reader = new FileReader(configPath)) {

                return gson.fromJson(reader, ConfigObject.class);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public static boolean getDevelopmentStatus() {
        return Objects.requireNonNull(readConfig()).development;
    }
    private static void initializeDefaultConfig() {
        ConfigObject.CharacterConfig dylanConfig = new ConfigObject.CharacterConfig("dylan");
        dylanConfig.presentConfig.countUnspecifiedItemsAsNeutral = false;
        dylanConfig.presentConfig.positive = Arrays.asList(
                "seasonal_adventures:beef_tartare",
                "candlelight:beef_tartare",
                "minecraft:cooked_mutton",
                "minecraft:cooked_beef",
                "minecraft:cooked_porkchop",
                "minecraft:diamond",
                "minecraft:netherite_ingot",
                "minecraft:cookie",
                "minecraft:cooked_chicken",
                "minecraft:cooked_cod",
                "minecraft:cooked_salmon",
                "minecraft:cake");
        dylanConfig.presentConfig.neutral = Arrays.asList(
                "seasonal_adventures:lockpick",
                "minecraft:book",
                "minecraft:compass",
                "minecraft:clock",
                "minecraft:spyglass",
                "minecraft:paper",
                "minecraft:lightning_rod",
                "minecraft:redstone",
                "minecraft:blaze_powder",
                "minecraft:tnt"
        );
        defaultConfig.characterConfigs.add(dylanConfig);
    }

    public static boolean isValidConfig(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            gson.fromJson(reader, ConfigObject.class);
            return true;
        } catch (JsonSyntaxException e) {
            SeasonalAdventures.LOGGER.error("Invalid JSON syntax: {}", e.getMessage());
        } catch (IOException e) {
            SeasonalAdventures.LOGGER.error("Error reading file: {}", e.getMessage());
        }
        return false;
    }
}
