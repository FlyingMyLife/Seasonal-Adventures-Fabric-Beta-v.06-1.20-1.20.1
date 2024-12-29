package net.packages.seasonal_adventures.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.packages.seasonal_adventures.gui.hud.SparkHudRenderer;

public class HudRenderCallbackHandler implements HudRenderCallback {
    @Environment(EnvType.CLIENT)
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        //SparkHudRenderer.render(drawContext);
    }
}
