package dev.flyingmylife.seasonal_adventures.network.packet.c2s;

import dev.flyingmylife.seasonal_adventures.SA;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import dev.flyingmylife.seasonal_adventures.item.custom.CardItem;
import dev.flyingmylife.seasonal_adventures.network.payload.banking.BankingOperationType;
import dev.flyingmylife.seasonal_adventures.util.game.InventoryUtils;
import dev.flyingmylife.seasonal_adventures.world.data.PlayerLinkedData;
import dev.flyingmylife.seasonal_adventures.world.data.persistent_state.WorldDataPersistentState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static dev.flyingmylife.seasonal_adventures.network.payload.banking.BankingPayloads.*;
import static net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.Context;
public class BankingOperationsPacket {

    public static final Logger LOGGER = LoggerFactory.getLogger("JDBank");

    public static void executeBasicOperations(BankingOperationType type, int amount) {
        ClientPlayNetworking.send(new BasicOperationPayload(type.id, amount));
    }
    public static void executeRequestCardOperation() {
        ClientPlayNetworking.send(new RequestCardOperationPayload());
    }
    public static void executeSendWarningOperation(UUID ownerUUID) {
        ClientPlayNetworking.send(new WarningOperationPayload(ownerUUID.toString()));
    }
    public static void executeSendWarningOperation(String ownerUUID) {
        ClientPlayNetworking.send(new WarningOperationPayload(ownerUUID));
    }
    public static void executeFineOperation(int fineAmount, String reason) {
        ClientPlayNetworking.send(new FineOperationPayload(fineAmount, reason));
    }
    public static boolean getPlayerCardStatus(MinecraftServer server, PlayerEntity player) {
        return WorldDataPersistentState.getServerState(server).playerBankingData.containsKey(player.getUuid());
    }

    private static String generateUniqueCardId(MinecraftServer server) {
        WorldDataPersistentState state = WorldDataPersistentState.getServerState(server);
        Random random = new Random();
        String cardId = "-";

        String finalCardId = cardId;
        do {
            cardId = String.format("%08d", random.nextInt(100000000));
        } while (state.playerBankingData.values().stream().anyMatch(data -> data.cardId.equals(finalCardId)));

        SA.LOGGER.info("Generated unique card ID: {}", cardId);
        return cardId;
    }

    public static void registerBasicOperation(BasicOperationPayload payload, Context context) {
        String typeId = payload.typeId();
        int amount = payload.amount();
        if (typeId.equals(BankingOperationType.REPLENISH.id)) {
            PlayerLinkedData playerState = WorldDataPersistentState.getPlayerState(context.player(), context.server());
            playerState.balance += amount;
            LOGGER.info("Processed operation {Operation type: REPLENISH, current balance: {}V, cardId: {}, replenished: {}V, player: {}}", playerState.balance, playerState.cardId, amount, playerState.nickname);
        } else if (typeId.equals(BankingOperationType.WITHDRAW.id)) {
            PlayerLinkedData playerState = WorldDataPersistentState.getPlayerState(context.player(), context.server());
            playerState.balance -= amount;
            LOGGER.info("Processed operation {Operation type: WITHDRAWAL, current balance: {}, cardId: {}, removed: {}V, player: {}}", playerState.balance, playerState.cardId, amount, playerState.nickname);
        }
    }

    public static void registerRequestCardOperation(RequestCardOperationPayload payload, Context context) {
        PlayerEntity player = context.player();
        if (InventoryUtils.hasFreeSlot(player)) {
            String cardId = generateUniqueCardId(context.server());
            WorldDataPersistentState.addNewPlayerToBankingSystem(player, cardId, context.server());
            ItemStack cardStack = CardItem.createCardItem(cardId);
            player.getInventory().insertStack(cardStack);
            MutableText thanksT = Text.translatable("message.seasonal_adventures.atm.success.card_received").formatted(Formatting.ITALIC, Formatting.WHITE);
            Text JdbT = Text.literal("JDB Team").formatted(Formatting.ITALIC, Formatting.GOLD, Formatting.BOLD);

            player.sendMessage(thanksT.append(JdbT), false);
            LOGGER.info("Registered new player in database with cardId: {}, nickname: {}", cardId, player.getName());
        } else {
            LOGGER.info("Failed to register new player in database, not enough space in player inventory!");
            player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.not_enough_space").formatted(Formatting.DARK_RED), true);
        }
    }

    public static void registerWarningOperation(WarningOperationPayload payload, Context context) {
        UUID ownerUUID = UUID.fromString(payload.ownerUUID());
        PlayerEntity player = context.player();
        PlayerEntity owner = context.server().getPlayerManager().getPlayer(ownerUUID);
        if (owner != null) {
            owner.sendMessage(Text.translatable("message.seasonal_adventures.atm.warn").formatted(Formatting.RED, Formatting.BOLD), true);
            LOGGER.info("Unauthorized access to {}'s card, contacting with owner!", owner.getName());
        } else {
            AtomicReference<String> ownerCardId = new AtomicReference<>();
            AtomicReference<String> ownerNickname = new AtomicReference<>();
            WorldDataPersistentState.getServerState(context.server()).playerBankingData.forEach(((uuid, playerLinkedData) -> {
                if (ownerUUID == uuid) {
                    ownerNickname.set(playerLinkedData.nickname);
                    ownerCardId.set(playerLinkedData.cardId);
                }
            }));
            LOGGER.info("Failed to contact with player {uuid: {}, nickname: {}, cardId: {}}", ownerUUID, ownerNickname.get(), ownerCardId.get());
        }
    }

    public static void registerFineOperation(FineOperationPayload payload, Context context) {
            PlayerLinkedData playerState = WorldDataPersistentState.getPlayerState(context.player(), context.server());
            playerState.balance -= payload.fineAmount();
            LOGGER.info("Processed operation {Operation type: FINE, current balance: {}, cardId: {}, fined: {}V, player: {}}", playerState.balance, playerState.cardId, payload.fineAmount(), playerState.nickname);
    }
}
