package net.packages.seasonal_adventures.events;

import com.google.gson.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.WorldSavePath;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.item.custom.CardItem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class JDBCardHandler {

    private static final Gson gson = new Gson();
    private static boolean initialized = false;
    private static File playerCardsFile;

    public static void initialize(MinecraftServer server, ServerWorld world) {
        ensureInitialized(server);
    }

    private static Path getWorldSavePath(MinecraftServer server) {
        return server.getSavePath(WorldSavePath.ROOT).toAbsolutePath();
    }

    public static void ensureInitialized(MinecraftServer server) {
        if (!initialized) {
            Path directoryPath = Paths.get(getWorldSavePath(server).toString(), "player_cards");
            File directory = directoryPath.toFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            playerCardsFile = new File(directory, "player_cards.json");

            if (!playerCardsFile.exists()) {
                createDefaultFile();
            } else {
                ensureFileIsNotEmpty();
            }

            initialized = true;
        }
    }

    private static void createDefaultFile() {
        JsonObject defaultJson = new JsonObject();
        JsonArray playersArray = new JsonArray();
        defaultJson.add("players", playersArray);

        try (FileWriter writer = new FileWriter(playerCardsFile)) {
            gson.toJson(defaultJson, writer);
            SeasonalAdventures.LOGGER.info("Cards file created with default structure: " + playerCardsFile.getAbsolutePath());
        } catch (IOException e) {
            SeasonalAdventures.LOGGER.error("Error creating default player cards file", e);
        }
    }

    private static void ensureFileIsNotEmpty() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(playerCardsFile.getPath())));
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();

            if (!json.has("players") || !json.get("players").isJsonArray()) {
                createDefaultFile();
            }
        } catch (IOException | JsonSyntaxException e) {
            SeasonalAdventures.LOGGER.error( "Error reading or parsing player cards file", e);
            createDefaultFile();
        }
    }

    private static void createFileIfNotExists() {
        if (!playerCardsFile.exists()) {
            createDefaultFile();
        }
    }

    private static JsonObject getPlayerObject(JsonArray playersArray, PlayerEntity player) {
        for (JsonElement element : playersArray) {
            JsonObject playerObject = element.getAsJsonObject();
            if (playerObject.get("uuid").getAsString().equals(player.getUuidAsString())) {
                return playerObject;
            }
        }
        return null;
    }

    public static JsonObject loadJsonFromFile() {
        File file = getPlayerCardsFile();

        if (file == null) {
            SeasonalAdventures.LOGGER.error("Player cards file is null.");
            return new JsonObject();
        }

        if (!file.exists()) {
            SeasonalAdventures.LOGGER.error("Player cards file does not exist at: " + file.getAbsolutePath());
            return new JsonObject();
        }

        try (FileReader reader = new FileReader(file)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (FileNotFoundException e) {
            SeasonalAdventures.LOGGER.error("Error reading player cards file: " + e.getMessage());
            return new JsonObject();
        } catch (IOException e) {
            SeasonalAdventures.LOGGER.error("IO Error: " + e.getMessage());
            return new JsonObject();
        }
    }


    private static void saveJsonToFile(JsonObject json, MinecraftServer server) {
        ensureInitialized(server);

        try (FileWriter writer = new FileWriter(playerCardsFile)) {
            gson.toJson(json, writer);
        } catch (IOException e) {
            SeasonalAdventures.LOGGER.error( "Error writing to player cards file", e);
            createFileIfNotExists();
        }
    }

    private static boolean isPlayerAlreadyHasCard(JsonArray playersArray, ClientPlayerEntity player) {
        for (JsonElement element : playersArray) {
            JsonObject playerObject = element.getAsJsonObject();
            if (playerObject.get("uuid").getAsString().equals(player.getUuidAsString())) {
                return true;
            }
        }
        return false;
    }

    private static String generateUniqueCardId(JsonArray playersArray) {
        Random random = new Random();
        String cardId;

        do {
            cardId = String.format("%08d", random.nextInt(100000000));
        } while (isCardIdUsed(playersArray, cardId));

        return cardId;
    }

    private static boolean isCardIdUsed(JsonArray playersArray, String cardId) {
        for (JsonElement element : playersArray) {
            JsonObject playerObject = element.getAsJsonObject();
            if (playerObject.get("card_id").getAsString().equals(cardId)) {
                return true;
            }
        }
        return false;
    }

    private static JsonObject createPlayerJsonObject(ServerPlayerEntity player, String cardId) {
        JsonObject playerObject = new JsonObject();
        playerObject.addProperty("uuid", player.getUuidAsString());
        playerObject.addProperty("nickname", player.getEntityName());
        playerObject.addProperty("card_id", cardId);
        return playerObject;
    }

    private static void giveCardItemToPlayer(ServerPlayerEntity player, String cardId) {
        ItemStack cardItem = CardItem.createCardItem(cardId, 0L);
        player.getInventory().insertStack(cardItem);
        Text thanksT = Text.translatable("message.seasonal_adventures.atm.success.card_received").formatted(Formatting.ITALIC, Formatting.WHITE);
        Text JdbT = Text.literal("JDB Team").formatted(Formatting.ITALIC, Formatting.GOLD, Formatting.BOLD);
        player.sendMessage(((MutableText) thanksT).append(JdbT), false);
    }

    public static boolean playerHasCard(PlayerEntity player) {
        JsonObject json = loadJsonFromFile();
        JsonArray playersArray = json.getAsJsonArray("players");
        JsonObject existingPlayerObject = getPlayerObject(playersArray, player);
        if (existingPlayerObject != null) {
            return true;
        }
        return false;
    }

    public static File getPlayerCardsFile() {
        return playerCardsFile;
    }

    public static JsonObject loadPlayerCards() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(getPlayerCardsFile().getPath())));
            return JsonParser.parseString(content).getAsJsonObject();
        } catch (IOException e) {
            SeasonalAdventures.LOGGER.error("Failed to read player_cards file");
            return new JsonObject();
        }
    }

    public static void giveCard(ServerPlayerEntity player, MinecraftServer server, ServerWorld overworld) {
        ensureInitialized(server);

        JsonObject json = loadJsonFromFile();
        if (json == null) {
            json = new JsonObject();
            json.add("players", new JsonArray());
        }

        JsonArray playersArray = json.getAsJsonArray("players");

        JsonObject existingPlayerObject = getPlayerObject(playersArray, player);
        if (existingPlayerObject != null) {
            return;
        }

        String cardId = generateUniqueCardId(playersArray);
        JsonObject newPlayerObject = createPlayerJsonObject(player, cardId);
        playersArray.add(newPlayerObject);
        saveJsonToFile(json, server);
        giveCardItemToPlayer(player, cardId);
    }
}
