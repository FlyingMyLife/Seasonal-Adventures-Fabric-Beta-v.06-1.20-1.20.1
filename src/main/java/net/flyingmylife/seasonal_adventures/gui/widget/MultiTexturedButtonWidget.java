package net.flyingmylife.seasonal_adventures.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.flyingmylife.seasonal_adventures.gui.data.ButtonRenderData;

public class MultiTexturedButtonWidget extends ButtonWidget {
    private final ButtonRenderData renderData;

    public MultiTexturedButtonWidget(int x, int y, ButtonRenderData renderData, Text message, PressAction onPress) {
        super(x, y, renderData.getWidth(), renderData.getHeight(), message, onPress, textSupplier -> Text.empty());
        this.renderData = renderData;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.drawTexture(renderData.getTexture(active), renderData.getTextureWidth(), renderData.getTextureHeight(), renderData.getU(), renderData.getV(hovered), getX(), getY(), renderData.getWidth(), renderData.getHeight());
        int i = this.active ? 16777215 : 10526880;
        this.drawMessage(context, minecraftClient.textRenderer, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }
}
