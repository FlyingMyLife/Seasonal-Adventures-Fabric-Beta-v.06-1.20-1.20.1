package net.flyingmylife.seasonal_adventures.event.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.entity.SAEntities;
import net.flyingmylife.seasonal_adventures.gui.data.EntityTrackingPool;
import net.flyingmylife.seasonal_adventures.gui.utils.RenderingUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Objects;

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
        Identifier atmId = EntityType.getId(SAEntities.ATM);
        List<EntityTrackingPool.Entry> atms = EntityTrackingPool.getEntityPool(atmId);

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
