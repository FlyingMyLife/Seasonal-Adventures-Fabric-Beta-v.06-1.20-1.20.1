package net.flyingmylife.seasonal_adventures.mixin.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.flyingmylife.seasonal_adventures.SA;
import net.flyingmylife.seasonal_adventures.config.SAClientConfig;
import net.flyingmylife.seasonal_adventures.gui.data.ButtonRenderData;
import net.flyingmylife.seasonal_adventures.gui.screen.SAConfigScreen;
import net.flyingmylife.seasonal_adventures.gui.widget.MultiTexturedButtonWidget;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void init(int y, int spacingY, CallbackInfo ci) {
        SAClientConfig.ClientConfigButtonPosition buttonPosition = Objects.requireNonNull(SAClientConfig.HANDLER.readConfig()).configButtonPos;
        MultiTexturedButtonWidget seasonalAdventuresConfigButton = getTexturedButtonWidget(y, spacingY, buttonPosition);
        addDrawableChild(seasonalAdventuresConfigButton);
        if (buttonPosition == SAClientConfig.ClientConfigButtonPosition.HIDDEN && FabricLoader.getInstance().isModLoaded("modmenu")) {
            seasonalAdventuresConfigButton.active = false;
        }
    }

    @Unique
    private @NotNull MultiTexturedButtonWidget getTexturedButtonWidget(int y, int spacingY, SAClientConfig.ClientConfigButtonPosition buttonPosition) {
        int x = this.width / 2 + 100 + 4;
        if (buttonPosition == SAClientConfig.ClientConfigButtonPosition.LEFT_MULTIPLAYER) {
            x = this.width / 2 - 124;
        }
        return new MultiTexturedButtonWidget(x, y - spacingY, ButtonRenderData.of(0, 0, 20, 20, 20, 32, 64, Identifier.of(SA.MOD_ID, "title_screen/sa_config_button")), Text.empty(), (button -> {
            MinecraftClient.getInstance().setScreen(new SAConfigScreen(Text.empty()));
        }));
    }
}
