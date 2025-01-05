package net.packages.seasonal_adventures.world.data.persistent_state;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.world.PlayerLinkedData;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class WorldDataPersistentState extends PersistentState {

    public HashMap<UUID, PlayerLinkedData> playerBankingData = new HashMap<>();

    public static boolean initializedDimensionOfDreams = false;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playersNbt = new NbtCompound();
        playerBankingData.forEach((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();
            playerNbt.putInt("currencyAmount", playerData.balance);
            playerNbt.putString("cardId", Objects.requireNonNullElse(playerData.cardId, "null"));
            playerNbt.putString("nickname", Objects.requireNonNullElse(playerData.cardId, "null"));
            playersNbt.put(uuid.toString(), playerNbt);
        });
        nbt.putBoolean("initialized_dimension_of_dreams", initializedDimensionOfDreams);
        nbt.put("players", playersNbt);

        return nbt;
    }

    public static void addNewPlayerToBankingSystem(PlayerEntity player, String cardId, MinecraftServer server) {
        WorldDataPersistentState serverState = getServerState(server);
        UUID playerUuid = player.getUuid();
        if (!serverState.playerBankingData.containsKey(playerUuid)) {
            PlayerLinkedData newPlayerData = new PlayerLinkedData();

            newPlayerData.nickname = player.getName().getString();

            newPlayerData.balance = 0;
            newPlayerData.cardId = cardId;

            serverState.playerBankingData.put(playerUuid, newPlayerData);

            serverState.markDirty();
        }
    }

    public static PlayerLinkedData getPlayerState(LivingEntity player, MinecraftServer server) {
        WorldDataPersistentState serverState = getServerState(server);

        return serverState.playerBankingData.computeIfAbsent(player.getUuid(), uuid -> new PlayerLinkedData());
    }

    public static WorldDataPersistentState createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        WorldDataPersistentState state = new WorldDataPersistentState();

        NbtCompound playersNbt = tag.getCompound("players");
        initializedDimensionOfDreams = tag.getBoolean("initialized_dimension_of_dreams");
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
            playerData.balance = currencyAmount;
            playerData.cardId = cardId;
            playerData.nickname = nickname;

            UUID uuid = UUID.fromString(key);
            state.playerBankingData.put(uuid, playerData);
        });

        return state;
    }

    public static WorldDataPersistentState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = Objects.requireNonNull(server.getWorld(ServerWorld.OVERWORLD)).getPersistentStateManager();

        WorldDataPersistentState state = persistentStateManager.getOrCreate(
                nbt -> WorldDataPersistentState.createFromNbt(nbt, null),
                WorldDataPersistentState::new,
                "sa_world_data"
        );

        state.markDirty();
        return state;
    }
}
