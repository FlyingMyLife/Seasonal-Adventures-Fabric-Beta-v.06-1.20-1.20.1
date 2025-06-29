package net.flyingmylife.seasonal_adventures.network.packet.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.flyingmylife.seasonal_adventures.config.ai.AiPropertiesManager;

import static net.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes.S2C.*;

public class SecretKeyUpdatePacket {

    public static void requestSecretKeyUpdate(ServerPlayerEntity player, String key) {
        ServerPlayNetworking.send(player, new SecretKeyUpdatePayload(key));
    }

    public static void register(SecretKeyUpdatePayload payload, ClientPlayNetworking.Context context) {
        String key = payload.key();
        MinecraftClient client = context.client();
        if (client.player == null) return;
        try {
            if (AiPropertiesManager.isKeyValid(key)) {
                client.player.sendMessage(Text.literal("Ключ успешно подтверждён, функции нейросетей активированы!")
                        .formatted(Formatting.GREEN), true);
                AiPropertiesManager.saveSecretKey(key);
            } else {
                System.out.println("Неверный или неавторизованный API-ключ.");
            }
        } catch (Exception e) {
            client.player.sendMessage(Text.literal("При проверке ключа произошла ошибка. Пожалуйста, попробуйте позже.")
                    .formatted(Formatting.DARK_RED), true);
        }
    }
}
