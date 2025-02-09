package net.packages.seasonal_adventures.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.packages.seasonal_adventures.config.ConfigReader;
import net.packages.seasonal_adventures.gui.hud.SparkHudRenderer;

import java.util.Objects;
@Environment(EnvType.CLIENT)
public class HudRenderCallbackHandler implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        if (Objects.requireNonNull(ConfigReader.readConfig()).betaFeatures) {
            SparkHudRenderer spark = new SparkHudRenderer();
            spark.render(drawContext);
        }
    }
}
