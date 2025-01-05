package net.packages.seasonal_adventures.gui.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MultiTextureButtonWidget extends TexturedButtonWidget {
    private final Text message;
    private final Identifier disabledTexture;

    public MultiTextureButtonWidget(int x, int y, int width, int height, int u, int v, int hoverV, Identifier texture, Identifier disabledTexture, int textureWidth, int textureHeight, PressAction onPress, Text message) {
        super(x, y, width, height, u, v, hoverV, texture, textureWidth, textureHeight, onPress);
        this.message = message;
        this.disabledTexture = disabledTexture;
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderButton(context, mouseX, mouseY, delta);
        int textColor = this.active ? 0xFFFFFF : 0xA0A0A0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (!active) {
            if (client.textRenderer != null) {
                int textX = this.getX() + (this.width - client.textRenderer.getWidth(this.message)) / 2;
                int textY = this.getY() + (this.height - 8) / 2;
                context.drawText(client.textRenderer, this.message, textX, textY, textColor, false);
            }
            this.drawTexture(context, this.disabledTexture, this.getX(), this.getY(), this.u, this.v, this.hoveredVOffset, this.width, this.height, this.textureWidth, this.textureHeight);
        } else {
            if (client.textRenderer != null) {
                int textX = this.getX() + (this.width - client.textRenderer.getWidth(this.message)) / 2;
                int textY = this.getY() + (this.height - 8) / 2;
                context.drawText(client.textRenderer, this.message, textX, textY, textColor, false);
            }
        }
    }
}