package net.packages.seasonal_adventures.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.packages.seasonal_adventures.config.ConfigLoader;
import net.packages.seasonal_adventures.gui.hud.SparkHudRenderer;

import java.util.Objects;

public class HudRenderCallbackHandler implements HudRenderCallback {
    @Environment(EnvType.CLIENT)
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (Objects.requireNonNull(ConfigLoader.readConfig()).betaFeatures) {
            SparkHudRenderer spark = new SparkHudRenderer();
            spark.render(drawContext);
        }
    }
}
