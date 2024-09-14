package net.packages.flying_machines.item.custom;

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

public class SanchezGlasses extends Item {
    public SanchezGlasses(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            Style style = Style.EMPTY
                    .withColor(TextColor.fromFormatting(Formatting.DARK_PURPLE))  // Set text color to gray
                    .withItalic(true);
            tooltip.add(Text.translatable("tooltip.flying_machines.sanchez_glasses.detailed").setStyle(style));
            tooltip.add(Text.translatable("tooltip.flying_machines.sanchez_glasses.detailed_upper").setStyle(style));
        } else {
            tooltip.add(Text.translatable("tooltip.flying_machines.sanchez_glasses.hint").formatted(Formatting.GRAY));
        }
    }
}
