package net.packages.seasonal_adventures.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class RenderingUtils {
    public static void renderItemTooltipIfHovered(DrawContext context, TextRenderer renderer, PlayerEntity player, ItemStack item, int x, int y, int mouseX, int mouseY) {
        if (isItemWithinBounds(x , y , mouseX, mouseY)) {
            context.drawTooltip(renderer, item.getTooltip(player, TooltipContext.BASIC), mouseX, mouseY);
        }
    }

    public static void renderItemWithTooltip (DrawContext context, ItemStack item, PlayerEntity player, TextRenderer renderer, int x, int y, int mouseX, int mouseY) {
        context.drawItem(item, x, y);
        renderItemTooltipIfHovered(context, renderer, player,item, x, y, mouseX, mouseY);
    }
    protected static boolean isItemWithinBounds(int x, int y, double pointX, double pointY) {

        return pointX >= (double)(x - 1) && pointX < (double)(x + 16 + 1) && pointY >= (double)(y - 1) && pointY < (double)(y + 16 + 1);
    }
    public static boolean isMouseWithinBounds(int x, int y, int width, int height, double pointX, double pointY) {

        return pointX >= (double)(x - 1) && pointX < (double)(x + width + 1) && pointY >= (double)(y - 1) && pointY < (double)(y + height + 1);
    }
}
