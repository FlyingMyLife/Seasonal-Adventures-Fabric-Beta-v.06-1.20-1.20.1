package net.packages.seasonal_adventures.gui.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.systems.RenderSystem;

public class CustomTexturedButtonWidget extends TexturedButtonWidget {
    private final Text message;
    private final Identifier disabledTexture;

    public CustomTexturedButtonWidget(int x, int y, int width, int height, int u, int v, int hoverV, Identifier texture, Identifier disabledTexture, int textureWidth, int textureHeight, PressAction onPress, Text message) {
        super(x, y, width, height, u, v, hoverV, texture, textureWidth, textureHeight, onPress);
        this.message = message;
        this.disabledTexture = disabledTexture;
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        Identifier textureToUse = this.active ? this.texture : this.disabledTexture;
        context.drawTexture(textureToUse, this.getX(), this.getY(), this.width, this.height, this.u, this.v, this.width, this.height, this.textureWidth, this.textureHeight);

        int textColor = this.active ? 0xFFFFFF : 0xA0A0A0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.textRenderer != null) {
            int textX = this.getX() + (this.width - client.textRenderer.getWidth(this.message)) / 2;
            int textY = this.getY() + (this.height - 8) / 2;
            context.drawText(client.textRenderer, this.message, textX, textY, textColor, false);
        }

        RenderSystem.disableBlend();
    }
}
