package net.packages.seasonal_adventures.config.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import net.packages.seasonal_adventures.SeasonalAdventures;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class ServerConfig {
    boolean isAtmsBreakable;

    public ServerConfig(boolean isAtmsBreakable) {
        this.isAtmsBreakable = isAtmsBreakable;
    }

    public static class HANDLER {
        private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        public static String configPath = FabricLoader.getInstance().getConfigDir().toString() + "\\sa-config.json";
        public static ServerConfig defaultConfig = new ServerConfig(true);

        public static ServerConfig readConfig() {
            if (isValidConfig(configPath)) {
                SeasonalAdventures.LOGGER.error("Failed to read client config file, using default settings...");
                initializeConfig();
                return defaultConfig;
            } else {
                try (Reader reader = new FileReader(configPath)) {

                    return gson.fromJson(reader, ServerConfig.class);

                } catch (IOException e) {
                    SeasonalAdventures.LOGGER.error("Failed to read client config [IOException], e:{}", String.valueOf(e));
                    return null;
                }
            }
        }

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

        public static boolean isValidConfig(String filePath) {
            try (FileReader reader = new FileReader(filePath)) {
                gson.fromJson(reader, ServerConfig.class);
                return false;
            } catch (JsonSyntaxException e) {
                SeasonalAdventures.LOGGER.error("Invalid JSON syntax: {}", e.getMessage());
            } catch (IOException e) {
                SeasonalAdventures.LOGGER.error("Error reading file: {}", e.getMessage());
            }
            return true;
        }
    }
}
