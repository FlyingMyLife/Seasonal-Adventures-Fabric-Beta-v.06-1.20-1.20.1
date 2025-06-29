package net.flyingmylife.seasonal_adventures.gui.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.Text;
import net.minecraft.client.util.math.MatrixStack;
import net.flyingmylife.seasonal_adventures.gui.data.ButtonRenderData;

public class LockpickWidget extends ButtonWidget {
    private float rotationAngle = 0.0f;
    private final float speedMultiplier;
    private boolean isCounterClockwise;
    private float accumulatedTime = 0.0f;
    private final ButtonRenderData renderData;

    public LockpickWidget(int x, int y, ButtonRenderData renderData, float speedMultiplier, PressAction onPress) {
        super(x, y, renderData.getWidth(), renderData.getHeight(), Text.empty(), onPress, textSupplier -> Text.empty());
        this.speedMultiplier = speedMultiplier;
        this.renderData = renderData;
    }

    public void toggleRotationDirection() {
        this.isCounterClockwise = !this.isCounterClockwise;
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        updateRotation(delta);
        MatrixStack matrixStack = context.getMatrices();
        matrixStack.push();
        matrixStack.translate(this.getX() + this.width / 2.0f, this.getY() + this.height / 2.0f, 0.0f);
        matrixStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotationDegrees(rotationAngle));
        matrixStack.translate(-this.getX() - this.width / 2.0f, -this.getY() - this.height / 2.0f, 0.0f);

        context.drawGuiTexture(RenderLayer::getGuiTextured, renderData.getTexture(active), renderData.getTextureWidth(), renderData.getTextureHeight(), renderData.getU(), renderData.getV(hovered), getX(), getY(), renderData.getWidth(), renderData.getHeight());

        matrixStack.pop();
    }

    private void updateRotation(float delta) {
        accumulatedTime += delta;

        while (accumulatedTime >= 1.0f / 60.0f) {
            float rotationAmount = speedMultiplier / 60.0f;
            if (!isCounterClockwise) {
                rotationAngle += rotationAmount;
            } else {
                rotationAngle -= rotationAmount;
            }

            rotationAngle %= 360.0f;
            if (rotationAngle < 0) {
                rotationAngle += 360.0f;
            }
            accumulatedTime -= 1.0f / 60.0f;
        }
    }

    public float getRotationAngle() {
        return this.rotationAngle;
    }

}
