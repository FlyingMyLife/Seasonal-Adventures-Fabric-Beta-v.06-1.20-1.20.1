package net.packages.seasonal_adventures.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.config.object.ConfigObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class ConfigReader {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static String configPath = FabricLoader.getInstance().getConfigDir().toString() + "\\seasonal_adventures.json";
    public static ConfigObject defaultConfig = new ConfigObject(false, false, true, false);

    public static void initializeConfig() {
        if (isValidConfig(configPath)) {
            SeasonalAdventures.LOGGER.info(FabricLoader.getInstance().getConfigDir().toString());
            try (FileWriter writer = new FileWriter(configPath)) {
                gson.toJson(defaultConfig, writer);
                SeasonalAdventures.LOGGER.info("Default config file created");
            } catch (IOException e) {
                SeasonalAdventures.LOGGER.error("Failed to initialize config [IOException], e:{}", String.valueOf(e));
            }
        }
    }

    public static void writeConfig(ConfigObject config) {
        try (FileWriter writer = new FileWriter(configPath)) {
            gson.toJson(config, writer);
            SeasonalAdventures.LOGGER.info("Updated config file");
        } catch (IOException e) {
            SeasonalAdventures.LOGGER.error("Failed to write config [IOException], e:{}", String.valueOf(e));
        }
    }

    public static ConfigObject readConfig() {
        if (isValidConfig(configPath)) {
            SeasonalAdventures.LOGGER.error("Failed to read config file, using default settings...");
            initializeConfig();
            return defaultConfig;
        } else {
            try (Reader reader = new FileReader(configPath)) {

                return gson.fromJson(reader, ConfigObject.class);

            } catch (IOException e) {
                SeasonalAdventures.LOGGER.error("Failed to read config [IOException], e:{}", String.valueOf(e));
                return null;
            }
        }
    }

    public static boolean isValidConfig(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            gson.fromJson(reader, ConfigObject.class);
            return false;
        } catch (JsonSyntaxException e) {
            SeasonalAdventures.LOGGER.error("Invalid JSON syntax: {}", e.getMessage());
        } catch (IOException e) {
            SeasonalAdventures.LOGGER.error("Error reading file: {}", e.getMessage());
        }
        return true;
    }
}
