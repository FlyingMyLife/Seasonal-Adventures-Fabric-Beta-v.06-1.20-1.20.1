package net.packages.seasonal_adventures.events;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.packages.seasonal_adventures.SeasonalAdventures;
import org.joml.Math;

public class HudRenderCallbackHandler implements HudRenderCallback {
    private static final Identifier SPARK_TEXTURE = new Identifier(SeasonalAdventures.MOD_ID, "textures/gui/hud/spark.png");
    private static final int sparkSize = 16;
    private float sparkAngle = 0f;
    private int sparkDirection = 1;
    private float sparkSpeed = 0.2f;
    private static final float sparkTargetAngle = 16f;

    private void sparkRenderer(DrawContext drawContext, float tickDelta) {
        sparkAngle += sparkSpeed * sparkDirection * tickDelta;

        if (sparkAngle >= sparkTargetAngle || sparkAngle <= 0) {
            sparkDirection *= -1;
            sparkSpeed = (sparkDirection == 1) ? sparkSpeed * 1.01f : sparkSpeed * 0.99f;
        }

        float sparkAngle = this.sparkAngle;
        MatrixStack matrixStack = drawContext.getMatrices();

        float x = drawContext.getScaledWindowWidth() - sparkSize;
        float y = (drawContext.getScaledWindowHeight() / 3f) * 2f;

        matrixStack.push();

        matrixStack.translate(x, y, 0);
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotation(Math.toRadians(sparkAngle)));

        drawContext.drawTexture(SPARK_TEXTURE,-sparkSize / 2,-sparkSize / 2, 0, 0, sparkSize, sparkSize, sparkSize, sparkSize);

        matrixStack.pop();
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        sparkRenderer(drawContext, tickDelta);
    }
}
