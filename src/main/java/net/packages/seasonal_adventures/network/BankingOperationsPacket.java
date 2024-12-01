package net.packages.seasonal_adventures.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.world.PlayerLinkedData;
import net.packages.seasonal_adventures.world.data.PlayerDataPersistentState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankingOperationsPacket {

    public static Identifier ID = new Identifier(SeasonalAdventures.MOD_ID, "banking_operations");
    public static final Logger LOGGER = LoggerFactory.getLogger("JDBank");
    public static void executeOperation(OperationType type, int amount) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeEnumConstant(type);
        buf.writeInt(amount);
        ClientPlayNetworking.send(ID, buf);
    }
    public static void register(){
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            OperationType type = buf.readEnumConstant(OperationType.class);
            int amount = buf.readInt();
            server.execute( () -> {
                PlayerLinkedData playerState = PlayerDataPersistentState.getPlayerState(player);
                if (type == OperationType.REPLENISH) {
                    playerState.currencyAmount += amount;
                    LOGGER.info("Persistent state replenish {current amount: {}, cardId: {} }", playerState.currencyAmount,  playerState.cardId);
                } else if (type == OperationType.WITHDRAW) {
                    playerState.currencyAmount -= amount;
                    LOGGER.info("Persistent state withdrawal {current amount: {}, cardId: {} }", playerState.currencyAmount,  playerState.cardId);
                }
            });
        });
    }
}
