package net.packages.seasonal_adventures.item.custom;

import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.network.server.DimensionOfDreamsGeneratorPacket;
import net.packages.seasonal_adventures.network.server.LoadChunkPacket;
import net.packages.seasonal_adventures.util.game.AnimatedPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SkinthOfDreamsItem extends Item {
    public SkinthOfDreamsItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user instanceof AnimatedPlayer){
            var animationContainer = ((AnimatedPlayer) (user)).seasonalAdventuresGetModAnimation();

            KeyframeAnimation anim = PlayerAnimationRegistry.getAnimation(new Identifier(SeasonalAdventures.MOD_ID, "used_skinth_of_dreams"));

            var builder = anim.mutableCopy();

            anim = builder.build();
            animationContainer.setAnimation(new KeyframeAnimationPlayer(anim));

            LoadChunkPacket.loadChunkInDimensionOfDreams(0, 0);
            Thread timer = new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    DimensionOfDreamsGeneratorPacket.teleportToDimensionOfDreams();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            timer.start();
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Text tooltipText = Text.translatable("tooltip.seasonal_adventures.skinth_of_dreams").formatted(Formatting.LIGHT_PURPLE);
        Text tooltipTextNl = Text.translatable("tooltip.seasonal_adventures.skinth_of_dreams.nl").formatted(Formatting.LIGHT_PURPLE);
        tooltip.add(tooltipText);
        tooltip.add(tooltipTextNl);
    }
}
