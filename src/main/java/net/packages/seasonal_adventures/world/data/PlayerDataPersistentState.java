package net.packages.seasonal_adventures.world.data;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
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
            nbt.putInt("currencyAmount", playerData.currencyAmount);
            nbt.putInt("cardId", playerData.cardId);
            playersNbt.put(uuid.toString(), playerNbt);
        });
        nbt.put("players", playersNbt);

        return nbt;
    }

    public static PlayerLinkedData getPlayerState(LivingEntity player) {
        PlayerDataPersistentState serverState = getServerState(Objects.requireNonNull(player.getWorld().getServer()));

        PlayerLinkedData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerLinkedData());

        return playerState;
    }

    public static PlayerDataPersistentState createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        PlayerDataPersistentState state = new PlayerDataPersistentState();

        NbtCompound playersNbt = tag.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerLinkedData playerData = new PlayerLinkedData();
            int currencyAmount = -1;
            int cardId = -1;

            if (playersNbt.getCompound(key).contains("cardId")) {
                cardId = playersNbt.getCompound(key).getInt("cardId");
            } else {
                SeasonalAdventures.LOGGER.error("Failed to find NBT for cardId");
            }
            if (playersNbt.getCompound(key).contains("currencyAmount")) {
                currencyAmount = playersNbt.getCompound(key).getInt("currencyAmount");
            } else {
                SeasonalAdventures.LOGGER.error("Failed to find NBT for currencyAmount");
            }

            playerData.currencyAmount = currencyAmount;
            playerData.cardId = cardId;

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
