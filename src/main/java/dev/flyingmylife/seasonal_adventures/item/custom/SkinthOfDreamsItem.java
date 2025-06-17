package dev.flyingmylife.seasonal_adventures.item.custom;

import dev.flyingmylife.seasonal_adventures.SA;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import dev.flyingmylife.seasonal_adventures.network.packet.c2s.TransportToDODPacket;
import dev.flyingmylife.seasonal_adventures.network.packet.c2s.LoadChunkPacket;
import dev.flyingmylife.seasonal_adventures.util.game.AnimatedPlayer;
import dev.flyingmylife.seasonal_adventures.world.dimension.Dimensions;

import java.util.List;

public class SkinthOfDreamsItem extends Item {
    public SkinthOfDreamsItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof AnimatedPlayer && !user.getWorld().getRegistryKey().equals(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY)){
            var animationContainer = ((AnimatedPlayer) (user)).seasonalAdventuresGetModAnimation();

            KeyframeAnimation anim = (KeyframeAnimation) PlayerAnimationRegistry.getAnimation(Identifier.of(SA.MOD_ID, "used_skinth_of_dreams"));

            assert anim != null;
            var builder = anim.mutableCopy();

            anim = builder.build();
            animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));

            LoadChunkPacket.loadChunkInDimensionOfDreams(Identifier.of(SA.MOD_ID, "dimension_of_dreams"), 0, 0);
            Thread timer = new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    TransportToDODPacket.teleportToDimensionOfDreams();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            timer.start();
        } else if (user.getWorld().getRegistryKey().equals(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY)) {
            user.sendMessage(Text.translatable("message.seasonal_adventures.skinth_of_dreams.already_used").formatted(Formatting.LIGHT_PURPLE), true);
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
