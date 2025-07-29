package net.flyingmylife.seasonal_adventures.item.custom;

import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.List;

public class SanchezGlassesItem extends Item {
    public SanchezGlassesItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
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
