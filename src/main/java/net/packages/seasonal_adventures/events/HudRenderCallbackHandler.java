package net.packages.seasonal_adventures.events;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.gui.hud.SparkHudHandler;
import org.apache.commons.compress.archivers.dump.DumpArchiveEntry;
import org.joml.Math;

public class HudRenderCallbackHandler implements HudRenderCallback {
    @Environment(EnvType.CLIENT)
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        SparkHudHandler.render(drawContext);
    }
}
