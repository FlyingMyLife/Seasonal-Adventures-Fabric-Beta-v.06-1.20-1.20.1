package net.packages.seasonal_adventures.network.server;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.item.custom.CardItem;
import net.packages.seasonal_adventures.util.enums.BankingOperationType;
import net.packages.seasonal_adventures.util.game.InventoryUtils;
import net.packages.seasonal_adventures.util.game.ServerUtils;
import net.packages.seasonal_adventures.world.PlayerLinkedData;
import net.packages.seasonal_adventures.world.data.persistent_state.WorldDataPersistentState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BankingOperationsPacket {

    public static Identifier ID = Identifier.of(SeasonalAdventures.MOD_ID, "banking_operations_packet");
    public static final Logger LOGGER = LoggerFactory.getLogger("JDBank");
    public static void executeBasicOperations(BankingOperationType type, int amount) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeEnumConstant(type);
        buf.writeInt(amount);
        ClientPlayNetworking.send(ID, buf);
    }
    public static void executeRequestCardOperation() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeEnumConstant(BankingOperationType.REQUEST_CARD);
        buf.writeInt(-1);
        ClientPlayNetworking.send(ID, buf);
    }
    public static void executeSendWarningOperation(UUID ownerUUID) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeEnumConstant(BankingOperationType.SEND_WARNING);
        buf.writeUuid(ownerUUID);
    }
    public static void executeFineOperation(int fineAmount, String reason) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeEnumConstant(BankingOperationType.FINE);
        buf.writeInt(fineAmount);
        buf.writeString(reason);
        ClientPlayNetworking.send(ID, buf);
    }
    public static void executeTransferOperation(int amount, PlayerEntity sender, PlayerEntity recipient) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeEnumConstant(BankingOperationType.TRANSFER);
        buf.writeInt(amount);
        UUID senderUUID = sender.getUuid();
        buf.writeUuid(senderUUID);
        UUID recipientUUID = recipient.getUuid();
        buf.writeUuid(recipientUUID);
        ClientPlayNetworking.send(ID, buf);
    }
    public static boolean getPlayerCardStatus (MinecraftServer server, PlayerEntity player) {
        return WorldDataPersistentState.getServerState(server).playerBankingData.containsKey(player.getUuid());
    }
    private static String generateUniqueCardId(MinecraftServer server) {
        WorldDataPersistentState state = WorldDataPersistentState.getServerState(server);
        SeasonalAdventures.LOGGER.info("Got server state");
        Random random = new Random();
        String cardId = "";
        AtomicBoolean distinct = new AtomicBoolean(true);
        do {
            cardId = String.format("%08d", random.nextInt(100000000));
            String finalCardId = cardId;
            state.playerBankingData.forEach((uuid, playerData) -> {
                if (playerData.cardId == finalCardId) {
                    distinct.set(false);
                }
            });
        } while (!distinct.get());
        SeasonalAdventures.LOGGER.info("Generated");

        return cardId;
    }
    public static void register(){
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            BankingOperationType type = buf.readEnumConstant(BankingOperationType.class);
            int amount = buf.readInt();
            server.execute( () -> {
                if (type == BankingOperationType.REPLENISH) {
                    PlayerLinkedData playerState = WorldDataPersistentState.getPlayerState(player, server);
                    playerState.balance += amount;
                    LOGGER.info("Processed operation {Operation type: REPLENISH, current balance: {}V, cardId: {}, replenished: {}V, player: {}}", playerState.balance,  playerState.cardId, amount, playerState.nickname);
                } else if (type == BankingOperationType.WITHDRAW) {
                    PlayerLinkedData playerState = WorldDataPersistentState.getPlayerState(player, server);
                    playerState.balance -= amount;
                    LOGGER.info("Processed operation {Operation type: WITHDRAWAL, current balance: {}, cardId: {}, removed: {}V, player: {}}", playerState.balance,  playerState.cardId, amount, playerState.nickname);
                } else if (type == BankingOperationType.FINE) {
                    PlayerLinkedData playerState = WorldDataPersistentState.getPlayerState(player, server);
                    playerState.balance -= amount;
                    LOGGER.info("Processed operation {Operation type: FINE, current balance: {}, cardId: {}, fined: {}V, player: {}}", playerState.balance,  playerState.cardId, amount, playerState.nickname);
                } else if (type == BankingOperationType.SEND_WARNING){
                    UUID ownerUUID = buf.readUuid();
                    PlayerEntity owner = server.getPlayerManager().getPlayer(ownerUUID);
                    if (owner != null) {
                        owner.sendMessage(Text.translatable("message.seasonal_adventures.atm.warn", player.getEntityName().formatted(Formatting.RED, Formatting.BOLD), true));
                        LOGGER.info("Unauthorized access to {}'s card, contacting with owner!", owner.getEntityName());
                    } else {
                        AtomicReference<String> ownerCardId = new AtomicReference<>();
                        AtomicReference<String> ownerNickname = new AtomicReference<>();
                        WorldDataPersistentState.getServerState(server).playerBankingData.forEach(((uuid, playerLinkedData) -> {
                            if (ownerUUID == uuid) {
                                ownerNickname.set(playerLinkedData.nickname);
                                ownerCardId.set(playerLinkedData.cardId);
                            }
                        }));
                        LOGGER.info("Failed to contact with player {uuid: {}, nickname: {}, cardId: {}}", ownerUUID, ownerNickname.get(), ownerCardId.get());
                    }
                } else if (type == BankingOperationType.TRANSFER) {
                    PlayerEntity sender = server.getPlayerManager().getPlayer(buf.readUuid());
                    PlayerEntity recipient = server.getPlayerManager().getPlayer(buf.readUuid());

                    assert sender != null;
                    assert recipient != null;

                    PlayerLinkedData senderData = WorldDataPersistentState.getPlayerState(sender, server);
                    PlayerLinkedData recipientData = WorldDataPersistentState.getPlayerState(recipient, server);

                    senderData.balance -= amount;
                    recipientData.balance += amount;
                    LOGGER.info("Processed operation {Operation type: TRANSFER, recipientCardId: {}, senderCardId: {}, amountTransferred : {}V}", recipientData.cardId,  senderData.cardId, amount);
                } else if (type == BankingOperationType.REQUEST_CARD) {
                    if (InventoryUtils.hasFreeSlot(player)) {
                        String cardId = generateUniqueCardId(server);
                        WorldDataPersistentState.addNewPlayerToBankingSystem(player, cardId, server);
                        ItemStack cardStack = CardItem.createCardItem(cardId);
                        player.getInventory().insertStack(cardStack);
                        MutableText thanksT = Text.translatable("message.seasonal_adventures.atm.success.card_received").formatted(Formatting.ITALIC, Formatting.WHITE);
                        Text JdbT = Text.literal("JDB Team").formatted(Formatting.ITALIC, Formatting.GOLD, Formatting.BOLD);

                        player.sendMessage(thanksT.append(JdbT), false);
                        LOGGER.info("Registered new player in database with cardId: {}, nickname: {}", cardId, player.getEntityName());
                    } else {
                        LOGGER.info("Failed to register new player in database, not enough space in player inventory!");
                        player.sendMessage(Text.translatable("message.seasonal_adventures.atm.fail.not_enough_space").formatted(Formatting.DARK_RED), true);
                    }
                }
            });
        });
    }
}
