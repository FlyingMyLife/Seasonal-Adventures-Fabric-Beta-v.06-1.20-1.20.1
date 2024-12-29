package net.packages.seasonal_adventures.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SanchezGlassesItem extends Item {
    public SanchezGlassesItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            Style style = Style.EMPTY
                    .withColor(TextColor.fromFormatting(Formatting.DARK_PURPLE))
                    .withItalic(true);
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.sanchez_glasses.detailed").setStyle(style));
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.sanchez_glasses.detailed_upper").setStyle(style));
        } else {
            tooltip.add(Text.translatable("tooltip.seasonal_adventures.sanchez_glasses.hint").formatted(Formatting.GRAY));
        }
    }
}
