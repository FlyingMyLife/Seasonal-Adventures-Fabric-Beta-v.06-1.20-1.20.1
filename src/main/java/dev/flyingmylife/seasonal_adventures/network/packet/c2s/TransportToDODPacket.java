package dev.flyingmylife.seasonal_adventures.network.packet.c2s;

import dev.flyingmylife.seasonal_adventures.SA;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import dev.flyingmylife.seasonal_adventures.util.game.ServerUtils;
import dev.flyingmylife.seasonal_adventures.world.data.persistent_state.WorldDataPersistentState;
import dev.flyingmylife.seasonal_adventures.world.dimension.Dimensions;

import java.util.EnumSet;

import static dev.flyingmylife.seasonal_adventures.network.payload.SAPayloadTypes.C2S.DODGeneratorPayload;

public class TransportToDODPacket {

    public static void teleportToDimensionOfDreams() {
        ClientPlayNetworking.send(new DODGeneratorPayload());
    }

    public static void register(DODGeneratorPayload payload, ServerPlayNetworking.Context context) {
        ServerWorld world = context.server().getWorld(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY);
        assert world != null;

        BlockPos targetPos = new BlockPos(8, 21, 9);
        context.player().teleport(world, targetPos.getX(), targetPos.getY(), targetPos.getZ(), EnumSet.noneOf(PositionFlag.class), context.player().getYaw(), context.player().getPitch(), false);
        StatusEffectInstance spawnProtection = new
                StatusEffectInstance(StatusEffects.RESISTANCE,
                10 * 20,
                255,
                true,
                false);
        context.player().addStatusEffect(spawnProtection);

        WorldDataPersistentState state = WorldDataPersistentState.getServerState(context.server());
        if (!state.initializedDimensionOfDreams) {
            ServerUtils.placeStructure(world, Identifier.of("seasonal_adventures:island_of_dreams"), new BlockPos(0, 0, 0));
            for (int x = -8; x <= 24; x++) {
                for (int z = -8; z <= 24; z++) {
                    world.removeBlock(new BlockPos(x, -61, z), false);
                }
            }
           state.initializedDimensionOfDreams = true;
            SA.LOGGER.info("Generated start island in dimension of dreams");
        }
    }
}