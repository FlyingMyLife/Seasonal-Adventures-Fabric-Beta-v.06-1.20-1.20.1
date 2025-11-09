package net.flyingmylife.seasonal_adventures.item.custom;

import net.flyingmylife.seasonal_adventures.SA;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.flyingmylife.seasonal_adventures.util.game.ServerUtils;
import net.flyingmylife.seasonal_adventures.world.data.persistent_state.WorldDataPersistentState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.Resource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.flyingmylife.seasonal_adventures.network.packet.c2s.TransportToDODPacket;
import net.flyingmylife.seasonal_adventures.network.packet.c2s.LoadChunkPacket;
import net.flyingmylife.seasonal_adventures.util.game.AnimatedPlayer;
import net.flyingmylife.seasonal_adventures.world.dimension.Dimensions;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SkinthOfDreamsItem extends Item {
    public SkinthOfDreamsItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof AnimatedPlayer) {
            var animationContainer = ((AnimatedPlayer) (user)).seasonalAdventuresGetModAnimation();

            KeyframeAnimation anim = (KeyframeAnimation) PlayerAnimationRegistry.getAnimation(Identifier.of(SA.MOD_ID, "used_skinth_of_dreams"));

            assert anim != null;
            var builder = anim.mutableCopy();

            anim = builder.build();
            animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));
        }

        if (world instanceof ServerWorld) {
            if (user.getWorld().getRegistryKey().equals(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY)) {
                user.sendMessage(Text.translatable("message.seasonal_adventures.skinth_of_dreams.already_used").formatted(Formatting.LIGHT_PURPLE), true);
                return TypedActionResult.fail(user.getStackInHand(hand));
            }

                Thread timer = new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        ServerWorld dodWorld = user.getServer().getWorld(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY);
                        assert world != null;

                        user.teleport(dodWorld, 8.5f, 26, 9.5f, Set.of(), user.getYaw(), user.getPitch());
                        StatusEffectInstance spawnProtection = new
                                StatusEffectInstance(StatusEffects.RESISTANCE,
                                10 * 20,
                                255,
                                true,
                                false);
                        user.addStatusEffect(spawnProtection);

                        WorldDataPersistentState state = WorldDataPersistentState.getServerState(user.getServer());
                        if (!state.initializedDimensionOfDreams) {
                            assert dodWorld != null;
                            Identifier structurePath = Identifier.of(SA.MOD_ID, "structures/island_of_dreams.nbt");

                            Optional<Resource> resourceOpt = user.getServer().getResourceManager().getResource(structurePath);

                            Resource resource = resourceOpt.orElseThrow();
                            try (InputStream stream = resource.getInputStream()) {
                                NbtCompound structureNbt = NbtIo.readCompressed(stream, NbtSizeTracker.ofUnlimitedBytes());

                                StructureTemplate template = new StructureTemplate();
                                template.readNbt(world.getRegistryManager().getWrapperOrThrow(RegistryKeys.BLOCK), structureNbt);

                                StructurePlacementData placementData = new StructurePlacementData();

                                template.place(dodWorld, new BlockPos(0, 0, 0), new BlockPos(0, 0, 0), placementData, dodWorld.random, 2);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            for (int x = -8; x <= 24; x++) {
                                for (int z = -8; z <= 24; z++) {
                                    dodWorld.removeBlock(new BlockPos(x, -61, z), false);
                                }
                            }

                            state.initializedDimensionOfDreams = true;
                            SA.LOGGER.info("Generated start island in dimension of dreams");
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                timer.start();
            }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        Text tooltipText = Text.translatable("tooltip.seasonal_adventures.skinth_of_dreams").formatted(Formatting.LIGHT_PURPLE);
        Text tooltipTextNl = Text.translatable("tooltip.seasonal_adventures.skinth_of_dreams.nl").formatted(Formatting.LIGHT_PURPLE);
        tooltip.add(tooltipText);
        tooltip.add(tooltipTextNl);
    }
}
