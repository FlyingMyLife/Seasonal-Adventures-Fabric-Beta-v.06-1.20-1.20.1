package net.packages.seasonal_adventures.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.config.ConfigLoader;
import net.packages.seasonal_adventures.network.server.ApplyStatusEffectPacket;
import net.packages.seasonal_adventures.world.dimension.Dimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(DownloadingTerrainScreen.class)
public abstract class DownloadingTerrainMixin extends Screen {
    @Shadow public abstract void tick();

    @Shadow public abstract void close();

    protected DownloadingTerrainMixin() {
        super(Text.empty());
    }
    @Unique
    private float opacity = 1.0f;
    @Unique
    private final long loadStartTime = System.currentTimeMillis();
    @Unique
    private static final Identifier [] SPARK_TEXTURES = {null, new Identifier(SeasonalAdventures.MOD_ID, "textures/gui/loading_location/spark_loading_1.png"), new Identifier(SeasonalAdventures.MOD_ID, "textures/gui/loading_location/spark_loading_2.png"), new Identifier(SeasonalAdventures.MOD_ID, "textures/gui/loading_location/spark_loading_3.png"), new Identifier(SeasonalAdventures.MOD_ID, "textures/gui/loading_location/spark_loading_4.png")};
    @Unique
    private static Identifier BACKGROUND_TEXTURE = new Identifier(SeasonalAdventures.MOD_ID, "textures/gui/loading_location/dimension_of_dreams.png");

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        assert Objects.requireNonNull(client).player != null;
        assert client.player != null;
        if (client.player.getWorld().getRegistryKey().equals(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY) && Objects.requireNonNull(ConfigLoader.readConfig()).fancyLocationLoading) {
            ci.cancel();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
            context.drawTexture(BACKGROUND_TEXTURE, 0, 0, 0, 0, width, height, width, height);
            int textX = 20;
            int textY = height - 55;
            context.fill(textX, textY + 10, textX + 110, textY + 35,0x40000000);
            TextWidget textWidget = new TextWidget(textX, textY, 110, 45,Text.translatable("gui.seasonal_adventures.text.loading_location"), textRenderer);
            textWidget.setTextColor(0xc4cf0f);
            textWidget.setAlpha(opacity);
            textWidget.render(context, mouseX, mouseY, delta);
            int sparkSize = 50;
            int sparkX = (width - sparkSize) - 25;
            int sparkY = (height - sparkSize) - 25;
            RenderSystem.setShaderTexture(0, getSparkTexture((int) (System.currentTimeMillis() - loadStartTime)));
            context.drawTexture(getSparkTexture((int) (System.currentTimeMillis() - loadStartTime)), sparkX, sparkY, 0, 0, sparkSize, sparkSize, sparkSize, sparkSize);
        }
    }
    @Unique
    private Identifier getSparkTexture(int ms) {
        return SPARK_TEXTURES[(ms / 125) % 4 + 1];
    }
    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    public void tick(CallbackInfo ci) {
        assert client != null;
        assert client.player != null;
        if (client.player.getWorld().getRegistryKey().equals(Dimensions.DIMENSION_OF_DREAMS_LEVEL_KEY) && Objects.requireNonNull(ConfigLoader.readConfig()).fancyLocationLoading) {
            ci.cancel();
            if (this.client.player == null) {
                return;
            }
            if (System.currentTimeMillis() >= this.loadStartTime + 5000L) {
                opacity -= 0.05f;
            }
            BlockPos blockPos = this.client.player.getBlockPos();
            boolean bl = this.client.world != null && this.client.world.isOutOfHeightLimit(blockPos.getY());
            if ((bl || this.client.worldRenderer.isRenderingReady(blockPos) || this.client.player.isSpectator() || !this.client.player.isAlive()) && System.currentTimeMillis() >= this.loadStartTime + 6000L) {
                this.close();
            }
            ApplyStatusEffectPacket.applySpawnProtectionEffect();
            if (System.currentTimeMillis() >= this.loadStartTime + 30000L) {
                this.close();
            }
        }
    }
}
