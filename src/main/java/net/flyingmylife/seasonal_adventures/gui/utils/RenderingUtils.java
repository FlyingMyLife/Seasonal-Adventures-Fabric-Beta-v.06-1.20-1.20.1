package net.flyingmylife.seasonal_adventures.gui.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class RenderingUtils {
    public static void renderItemTooltipIfHovered(DrawContext context, TextRenderer renderer, PlayerEntity player, ItemStack item, int x, int y, int mouseX, int mouseY) {
        if (isItemWithinBounds(x , y , mouseX, mouseY)) {
            context.drawTooltip(renderer, item.getTooltip(Item.TooltipContext.DEFAULT, player, TooltipType.BASIC), mouseX, mouseY);
        }
    }

    public static void renderItemWithTooltip (DrawContext context, ItemStack item, PlayerEntity player, TextRenderer renderer, int x, int y, int mouseX, int mouseY) {
        context.drawItem(item, x, y);
        renderItemTooltipIfHovered(context, renderer, player,item, x, y, mouseX, mouseY);
    }

    protected static boolean isItemWithinBounds(int x, int y, double pointX, double pointY) {

        return pointX >= (double)(x - 1) && pointX < (double)(x + 16 + 1) && pointY >= (double)(y - 1) && pointY < (double)(y + 16 + 1);
    }
    public static boolean isMouseWithinBounds(int x, int y, int width, int height, double mouseX, double mouseY) {

        return mouseX >= (double)(x - 1) && mouseX < (double)(x + width + 1) && mouseY >= (double)(y - 1) && mouseY < (double)(y + height + 1);
    }

}
