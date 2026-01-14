package net.flyingmylife.seasonal_adventures.gui.data;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public record ButtonRenderData(int u, int v, int hoverV, int width, int height, int textureWidth, int textureHeight,
                               Identifier availableTexture, Identifier unavailableTexture) {

    public static ButtonRenderData of(int width, int height, Identifier texture) {
        return new ButtonRenderData(0, 0, 0, width, height, width, height, texture, texture);
    }

    public static ButtonRenderData of(int width, int height, Identifier availableTexture, Identifier unavailableTexture) {
        return new ButtonRenderData(0, 0, 0, width, height, width, height, availableTexture, unavailableTexture);
    }

    public static ButtonRenderData of(int u, int v, int width, int height, int textureWidth, int textureHeight, Identifier texture) {
        return new ButtonRenderData(u, v, 0, width, height, textureWidth, textureHeight, texture, texture);
    }

    public static ButtonRenderData of(int u, int v, int hoverV, int width, int height, int textureWidth, int textureHeight, Identifier texture) {
        return new ButtonRenderData(u, v, hoverV, width, height, textureWidth, textureHeight, texture, texture);
    }

    public static ButtonRenderData of(int u, int v, int width, int height, int textureWidth, int textureHeight, Identifier availableTexture, Identifier unavailableTexture) {
        return new ButtonRenderData(u, v, 0, width, height, textureWidth, textureHeight, availableTexture, unavailableTexture);
    }

    public static ButtonRenderData of(int u, int v, int hoverV, int width, int height, int textureWidth, int textureHeight, Identifier availableTexture, Identifier unavailableTexture) {
        return new ButtonRenderData(u, v, hoverV, width, height, textureWidth, textureHeight, availableTexture, unavailableTexture);
    }

    public int getV(boolean hovered) {
        return hovered ? hoverV : v;
    }

    public Identifier getTexture(boolean active) {
        return active ? availableTexture : unavailableTexture;
    }

    public @NotNull String toString() {
        return "[u = " + u + ", v = " + v + ", textureWidth = " + textureWidth + ", textureHeight = " + textureHeight() +
                ", width = " + width + ", height = " + height + ", availableTextureId = " + availableTexture.toString() +
                ", unavailableTextureId = " + unavailableTexture.toString() + "]";
    }
}
