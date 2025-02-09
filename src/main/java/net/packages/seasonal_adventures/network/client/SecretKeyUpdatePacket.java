package net.packages.seasonal_adventures.network.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.config.ai.AiPropertiesLoader;

public class SecretKeyUpdatePacket {
    private static final Identifier ID = Identifier.of(SeasonalAdventures.MOD_ID, "secret-key_update_packet");

    public static void requestSecretKeyUpdate(ServerPlayerEntity player, String key) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeString(key);
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ID, (client, handler, buf, responseSender) -> {
            client.execute(() -> {
                String key = buf.readString();
                assert client.player != null;
                try {
                    if (AiPropertiesLoader.isKeyValid(key)) {
                        client.player.sendMessage(Text.literal("Ключ успешно подтверждён, функции нейросетей активированы!")
                                .formatted(Formatting.GREEN));
                        AiPropertiesLoader.saveSecretKey(key);
                    } else {
                        System.out.println("Неверный или неавторизованный API-ключ.");
                    }
                } catch (Exception e) {
                    client.player.sendMessage(Text.literal("При проверке ключа произошла ошибка. Пожалуйста, попробуйте позже.")
                            .formatted(Formatting.DARK_RED));
                }

            });
        });
    }
}
