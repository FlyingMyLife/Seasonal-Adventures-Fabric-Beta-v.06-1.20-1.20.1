package net.packages.seasonal_adventures.gui.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;

public class RotatingLockpick extends TexturedButtonWidget {
    private float rotationAngle = 0.0f;
    private float speedMultiplier;
    private boolean isCounterClockwise;

    public RotatingLockpick(int x, int y, int width, int height, int u, int v, int hoverV, Identifier texture, int textureWidth, int textureHeight, float speedMultiplier, PressAction onPress) {
        super(x, y, width, height, u, v, hoverV, texture, textureWidth, textureHeight, onPress);
        this.speedMultiplier = speedMultiplier;
        this.isCounterClockwise = isCounterClockwise;
    }
    public void toggleRotationDirection() {
        this.isCounterClockwise = !this.isCounterClockwise;
    }

    public float getRotationAngle() {
        return this.rotationAngle;
    }
    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        if(!isCounterClockwise) rotationAngle += 0.7f * speedMultiplier;
        if(isCounterClockwise) rotationAngle -= 0.7f * speedMultiplier;

        rotationAngle %= 360.0f;
        if (rotationAngle < 0) {
            rotationAngle += 360.0f;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        RenderSystem.setShaderTexture(0, this.texture);

        MatrixStack matrixStack = context.getMatrices();
        matrixStack.push();
        matrixStack.translate(this.getX() + this.width / 2.0f, this.getY() + this.height / 2.0f, 0.0f);
        matrixStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotationDegrees(rotationAngle));
        matrixStack.translate(-this.getX() - this.width / 2.0f, -this.getY() - this.height / 2.0f, 0.0f);

        context.drawTexture(this.texture, this.getX(), this.getY(), this.width, this.height, this.u, this.v, this.width, this.height, this.textureWidth, this.textureHeight);

        matrixStack.pop();
        RenderSystem.disableBlend();
    }

}
