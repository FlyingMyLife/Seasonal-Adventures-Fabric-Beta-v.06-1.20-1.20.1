package net.flyingmylife.seasonal_adventures.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.event.custom.HudRenderCallbackHandler;
import net.flyingmylife.seasonal_adventures.world.dimension.Dimensions;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DownloadingTerrainScreen.class)
public abstract class DownloadingTerrainMixin extends Screen {
    @Shadow public abstract void tick();
    @Shadow public abstract void close();

    protected DownloadingTerrainMixin() {
        super(Text.empty());
    }


    @Unique private static final Identifier SPARK_TEXTURE = Identifier.of(SA.MOD_ID, "textures/gui/sprites/loading_location/spark_sprite.png");
    @Unique private static final Identifier BACKGROUND_TEXTURE = Identifier.of(SA.MOD_ID, "textures/gui/sprites/loading_location/dimension_of_dreams.png");
    @Unique private static final int[] SPARK_V = {0, 0, 49, 99, 149};
    @Unique private double totalDuration = 0.0;
    @Unique private double opacity = 1.0;
    @Unique private long animStartTime = System.currentTimeMillis();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void renderCustom(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (client.player != null && client.player.getWorld().getRegistryKey().equals(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY)) {
            ci.cancel();

            RenderSystem.enableBlend();
            final int baseWidth = 1920;
            final int baseHeight = 1080;

            float scale = Math.min((float) width / baseWidth, (float) height / baseHeight);
            int scaledWidth = (int) (baseWidth * scale);
            int scaledHeight = (int) (baseHeight * scale);
            int offsetX = (width - scaledWidth) / 2;
            int offsetY = (height - scaledHeight) / 2;

            context.fill(0, 0, width, height, ColorHelper.Argb.getArgb(255, 0, 0, 0));

            context.drawGuiTexture(BACKGROUND_TEXTURE, offsetX, offsetY, 0, 0, scaledWidth, scaledHeight, scaledWidth, scaledHeight);

            long elapsed = System.currentTimeMillis() - animStartTime;
            double fadeInDuration = 1200;
            double holdDuration = 3000;
            double fadeOutDuration = 2000;
            totalDuration = fadeInDuration + holdDuration + fadeOutDuration;

            double fadeProgress;
            if (elapsed < fadeInDuration) {
                fadeProgress = elapsed / fadeInDuration;
                opacity = 1.0 - easeInOutSine(fadeProgress);
            } else if (elapsed < fadeInDuration + holdDuration) {
                opacity = 0.0;
            } else if (elapsed < totalDuration) {
                fadeProgress = (elapsed - fadeInDuration - holdDuration) / fadeOutDuration;
                opacity = easeInOutSine(fadeProgress);
            } else {
                opacity = 1.0;
            }

            int textX = 20;
            int textY = height - 55;
            context.fill(textX, textY + 10, textX + 110, textY + 35, 0x40000000);

            TextWidget textWidget = new TextWidget(textX, textY, 110, 45,
                    Text.translatable("gui.seasonal_adventures.text.loading_location"), textRenderer);
            int alpha = (int) (opacity * 255.0);
            textWidget.setTextColor(ColorHelper.Argb.getArgb(Math.abs(255 - alpha), 196, 207, 15));
            textWidget.render(context, mouseX, mouseY, delta);

            int sparkSize = 50;
            int sparkX = (width - sparkSize) - 25;
            int sparkY = (height - sparkSize) - 25;
            context.drawGuiTexture(SPARK_TEXTURE, sparkX, sparkY, 0,
                    getSparkV((int) (System.currentTimeMillis() - animStartTime)),
                    sparkSize, sparkSize, 50, 200);


            context.fill(0, 0, width, height, ColorHelper.Argb.getArgb(alpha, 0, 0, 0));

            RenderSystem.disableBlend();
        }
    }

    @Unique
    private int getSparkV(int ms) {
        return SPARK_V[(ms / 125) % 4 + 1];
    }

    @Unique
    private double easeInOutSine(double x) {
        return -(Math.cos(Math.PI * x) - 1.0) / 2.0;
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        if (client.player != null && client.player.getWorld().getRegistryKey().equals(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY)) {
            ci.cancel();

            long elapsed = System.currentTimeMillis() - animStartTime;

            if (elapsed >= totalDuration && (client.worldRenderer.isRenderingReady(client.player.getBlockPos()) || elapsed >= 30000)) {
                HudRenderCallbackHandler.startFade(2D);
                this.close();
            }
        }
    }
}