package net.packages.seasonal_adventures.gui.hud;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.packages.seasonal_adventures.SeasonalAdventures;
import org.joml.Math;

public class SparkHudRenderer {
    private static final Identifier SPARK_TEXTURE = new Identifier(SeasonalAdventures.MOD_ID, "textures/gui/hud/spark.png");
    private static final int sparkSize = 12;
    private static float sparkAngle = 0f;
    private static float sparkPos = 0f;
    private static final float totalRotation = 12f;
    private static final long cycleTime = 10 * 1000;
    private static long startTime;
    private static boolean isAnimating = false;

    public SparkHudRenderer() {
        startAnimation();
    }

    public static void startAnimation() {
        startTime = System.currentTimeMillis();
        isAnimating = true;
    }

    public static void render(DrawContext drawContext) {

        final float sparkTargetPos = drawContext.getScaledWindowHeight() / 385f * 3.2f;

        if (!isAnimating) return;
        // Начать отсчёт времени
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        if (elapsedTime > cycleTime) {
            elapsedTime -= cycleTime;
            startAnimation();
        }

        // Фаза цикла
        float phase = (float) elapsedTime / cycleTime;

        if (phase <= 0.5f) {
            sparkAngle = totalRotation * (phase * 2);
            sparkPos = sparkTargetPos * (phase * 2);
        } else {
            sparkAngle = totalRotation * (1 - ((phase - 0.5f) * 2));
            sparkPos = sparkTargetPos * (1 - ((phase - 0.5f) * 2));
        }

        // Получаем координаты
        MatrixStack matrixStack = drawContext.getMatrices();

        float x = drawContext.getScaledWindowWidth() - sparkSize;
        float y = (drawContext.getScaledWindowHeight() / 3f) * 2f - sparkPos;

        // Обновляем позицию и угол
        matrixStack.push();
        matrixStack.translate(x, y, 0);
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotation(Math.toRadians(sparkAngle)));
        drawContext.drawTexture(SPARK_TEXTURE, -sparkSize / 2, -sparkSize / 2, 0, 0, sparkSize, sparkSize, sparkSize, sparkSize);
        matrixStack.pop();
    }

}
