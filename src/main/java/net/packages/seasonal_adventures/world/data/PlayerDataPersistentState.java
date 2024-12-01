package net.packages.seasonal_adventures.world.data;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.util.WorldUtils;
import net.packages.seasonal_adventures.world.PlayerLinkedData;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class PlayerDataPersistentState extends PersistentState {

    public HashMap<UUID, PlayerLinkedData> players = new HashMap<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {

        NbtCompound playersNbt = new NbtCompound();
        players.forEach((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();
            playerNbt.putInt("currencyAmount", playerData.currencyAmount);
            playerNbt.putString("cardId", Objects.requireNonNullElse(playerData.cardId, "null"));
            playerNbt.putString("nickname", playerData.nickname);
            playersNbt.put(uuid.toString(), playerNbt);
        });
        nbt.put("players", playersNbt);

        return nbt;
    }

    public static void addNewPlayer(PlayerEntity player, String cardId) {
        PlayerDataPersistentState serverState = getServerState(Objects.requireNonNull(player.getServer()));

        UUID playerUuid = player.getUuid();
        if (!serverState.players.containsKey(playerUuid)) {
            PlayerLinkedData newPlayerData = new PlayerLinkedData();


            newPlayerData.nickname = player.getName().getString();

            newPlayerData.currencyAmount = 0;
            newPlayerData.cardId = cardId;

            serverState.players.put(playerUuid, newPlayerData);

            serverState.markDirty();
            SeasonalAdventures.LOGGER.info("Added new player with UUID: {} and nickname: {}", playerUuid, newPlayerData.nickname);
        }
    }

    public static PlayerLinkedData getPlayerState(LivingEntity player) {
        PlayerDataPersistentState serverState = getServerState(Objects.requireNonNull(player.getWorld().getServer()));

        return serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerLinkedData());
    }

    public static PlayerDataPersistentState createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        PlayerDataPersistentState state = new PlayerDataPersistentState();

        NbtCompound playersNbt = tag.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerLinkedData playerData = new PlayerLinkedData();
            int currencyAmount = -1;
            String cardId = null;
            String nickname = null;

            if (playersNbt.getCompound(key).contains("cardId")) {
                cardId = playersNbt.getCompound(key).getString("cardId");
            } else {
                SeasonalAdventures.LOGGER.info("Failed to find NBT <cardId> for player: {}, creating nbt...", key);
            }
            if (playersNbt.getCompound(key).contains("currencyAmount")) {
                currencyAmount = playersNbt.getCompound(key).getInt("currencyAmount");
            } else {
                SeasonalAdventures.LOGGER.info("Failed to find NBT <currencyAmount> for player: {}, creating nbt...", key);
            }
            if (playersNbt.getCompound(key).contains("nickname")) {
                nickname = playersNbt.getCompound(key).getString("nickname");
            }

            playerData.currencyAmount = currencyAmount;
            playerData.cardId = cardId;
            playerData.nickname = nickname;

            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playerData);
        });

        return state;
    }

    public static PlayerDataPersistentState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = Objects.requireNonNull(WorldUtils.getOverworld(server)).getPersistentStateManager();

        PlayerDataPersistentState state = persistentStateManager.getOrCreate(
                nbt -> PlayerDataPersistentState.createFromNbt(nbt, null),
                PlayerDataPersistentState::new,
                "player_data"
        );

        state.markDirty();
        return state;
    }
}
