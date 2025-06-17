package dev.flyingmylife.seasonal_adventures.gui.data;

import net.minecraft.util.Identifier;

public class ButtonRenderData {
    private final int u;
    private final int v;
    private final int hoverV;
    private final int width;
    private final int height;
    private final int textureWidth;
    private final int textureHeight;
    private final Identifier availableTexture;
    private final Identifier unavailableTexture;

    protected ButtonRenderData(int u, int v, int hoverV, int width, int height, int textureWidth, int textureHeight, Identifier availableTexture, Identifier unavailableTexture) {
        this.u = u;
        this.v = v;
        this.hoverV = hoverV;
        this.width = width;
        this.height = height;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.availableTexture = availableTexture;
        this.unavailableTexture = unavailableTexture;
    }
    public static ButtonRenderData of (int width, int height, Identifier texture) {
        return new ButtonRenderData(0, 0, 0, width, height, width, height, texture, texture);
    }
    public static ButtonRenderData of (int width, int height, Identifier availableTexture, Identifier unavailableTexture) {
        return new ButtonRenderData(0, 0, 0, width, height, width, height, availableTexture, unavailableTexture);
    }
    public static ButtonRenderData of (int u, int v, int width, int height, int textureWidth, int textureHeight, Identifier texture) {
        return new ButtonRenderData(u, v, 0, width, height, textureWidth, textureHeight, texture, texture);
    }
    public static ButtonRenderData of (int u, int v, int hoverV, int width, int height, int textureWidth, int textureHeight, Identifier texture) {
        return new ButtonRenderData(u, v, hoverV, width, height, textureWidth, textureHeight, texture, texture);
    }

    public static ButtonRenderData of (int u, int v, int width, int height, int textureWidth, int textureHeight, Identifier availableTexture, Identifier unavailableTexture) {
        return new ButtonRenderData(u, v, 0, width, height, textureWidth, textureHeight, availableTexture, unavailableTexture);
    }

    public static ButtonRenderData of (int u, int v, int hoverV, int width, int height, int textureWidth, int textureHeight, Identifier availableTexture, Identifier unavailableTexture) {
        return new ButtonRenderData(u, v, hoverV, width, height, textureWidth, textureHeight, availableTexture, unavailableTexture);
    }

    public int getU () {
        return u;
    }
    public int getV (boolean hovered) {
        return hovered ? hoverV : v;
    }
    public int getWidth () {
        return width;
    }
    public int getHeight () {
        return height;
    }
    public int getTextureWidth () {
        return textureWidth;
    }
    public int getTextureHeight () {
        return textureHeight;
    }
    public Identifier getTexture (boolean active) {
        return active ? availableTexture : unavailableTexture;
    }

    public String toString() {
        return "[u = " + u + ", v = " + v + ", textureWidth = " + textureWidth + ", textureHeight = " + getTextureHeight() +
                ", width = " + width + ", height = " + height + ", availableTextureId = " + availableTexture.toString() +
                ", unavailableTextureId = " + unavailableTexture.toString() + "]";
    }
}
