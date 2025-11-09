package net.flyingmylife.seasonal_adventures.event.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.math.ColorHelper;

@Environment(EnvType.CLIENT)
public class HudRenderCallbackHandler implements HudRenderCallback {
    private static double animDuration = 1.5;
    private static long startTime = -1;
    private static boolean finished = false;

    public static void startFade(double seconds) {
        animDuration = seconds;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (finished || startTime < 0) {
            startTime = -1;
            finished = false;
            animDuration = 1.5;
            return;
        }

        long elapsed = System.currentTimeMillis() - startTime;
        double t = Math.min(1.0, elapsed / (animDuration * 1000.0));
        double opacity = 1.0 - easeInOutSine(t);

        if (t >= 1.0) finished = true;

        int alpha = (int)(opacity * 255);
        MinecraftClient client = MinecraftClient.getInstance();
        drawContext.fill(0, 0, client.getWindow().getScaledWidth(),
                client.getWindow().getScaledHeight(),
                ColorHelper.Argb.getArgb(alpha, 0, 0, 0));
    }

    private double easeInOutSine(double x) {
        return -(Math.cos(Math.PI * x) - 1) / 2;
    }
}