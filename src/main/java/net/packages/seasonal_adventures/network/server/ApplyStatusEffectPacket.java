package net.packages.seasonal_adventures.network.server;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;

public class ApplyStatusEffectPacket {
    public static final Identifier ID = Identifier.of(SeasonalAdventures.MOD_ID, "apply_status_effect_packet");

    public static void applySpawnProtectionEffect() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        ClientPlayNetworking.send(ID, buf);
    }

    public static void register () {
        ServerPlayNetworking.registerGlobalReceiver(ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.RESISTANCE,
                        1,
                        255,
                        true,
                        false
                ));
            });
        });
    }
}
