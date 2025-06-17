package dev.flyingmylife.seasonal_adventures.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import dev.flyingmylife.seasonal_adventures.SA;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class SAClientConfig {
    public ClientConfigButtonPosition configButtonPos;
    public boolean fancyLocationLoading;

    public SAClientConfig(ClientConfigButtonPosition configButtonPos, boolean fancyLocationLoading) {
        this.configButtonPos = configButtonPos;
        this.fancyLocationLoading = fancyLocationLoading;
    }

    public enum ClientConfigButtonPosition {
        HIDDEN,

        DEFAULT,

        LEFT_MULTIPLAYER
    }

    public static class HANDLER {
        private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        public static String configPath = FabricLoader.getInstance().getConfigDir().toString() + "\\sa-client.json";
        public static SAClientConfig defaultConfig = new SAClientConfig(SAClientConfig.ClientConfigButtonPosition.DEFAULT, true);

        public static void initializeConfig() {
            if (isValidConfig(configPath)) {
                SA.LOGGER.info(FabricLoader.getInstance().getConfigDir().toString());
                try (FileWriter writer = new FileWriter(configPath)) {
                    gson.toJson(defaultConfig, writer);
                    SA.LOGGER.info("Default config file created");
                } catch (IOException e) {
                    SA.LOGGER.error("Failed to initialize config [IOException], e:{}", String.valueOf(e));
                }
            }
        }

        public static void writeConfig(SAClientConfig config) {
            try (FileWriter writer = new FileWriter(configPath)) {
                gson.toJson(config, writer);
                SA.LOGGER.info("Updated client config file");
            } catch (IOException e) {
                SA.LOGGER.error("Failed to write client config [IOException], e:{}", String.valueOf(e));
            }
        }

        public static SAClientConfig readConfig() {
            if (isValidConfig(configPath)) {
                SA.LOGGER.error("Failed to read client config file, using default settings...");
                initializeConfig();
                return defaultConfig;
            } else {
                try (Reader reader = new FileReader(configPath)) {

                    return gson.fromJson(reader, SAClientConfig.class);

                } catch (IOException e) {
                    SA.LOGGER.error("Failed to read client config [IOException], e:{}", String.valueOf(e));
                    return null;
                }
            }
        }

        public static boolean isValidConfig(String filePath) {
            try (FileReader reader = new FileReader(filePath)) {
                gson.fromJson(reader, SAClientConfig.class);
                return false;
            } catch (JsonSyntaxException e) {
                SA.LOGGER.error("Invalid JSON syntax: {}", e.getMessage());
            } catch (IOException e) {
                SA.LOGGER.error("Error reading file: {}", e.getMessage());
            }
            return true;
        }
    }
}
