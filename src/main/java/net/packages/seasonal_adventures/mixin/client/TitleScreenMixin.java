package net.packages.seasonal_adventures.mixin.client;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.config.ConfigLoader;
import net.packages.seasonal_adventures.config.object.ConfigObject;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
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
        ConfigObject.ConfigButtonPosition buttonPosition = Objects.requireNonNull(ConfigLoader.readConfig()).configButtonPos;
        TexturedButtonWidget seasonalAdventuresConfigButton = getTexturedButtonWidget(y, spacingY, buttonPosition);
        addDrawableChild(seasonalAdventuresConfigButton);
        if (buttonPosition == ConfigObject.ConfigButtonPosition.HIDDEN && FabricLoader.getInstance().isModLoaded("modmenu")) {
            seasonalAdventuresConfigButton.active = false;
        }
    }

    private @NotNull TexturedButtonWidget getTexturedButtonWidget(int y, int spacingY, ConfigObject.ConfigButtonPosition buttonPosition) {
        int x = this.width / 2 + 100 + 4;
        if (buttonPosition == ConfigObject.ConfigButtonPosition.LEFT_MULTIPLAYER) {
            x = this.width / 2 - 124;
        }
        return new TexturedButtonWidget(x, y + spacingY, 20, 20, 0, 0, 20,
                new Identifier(SeasonalAdventures.MOD_ID, "textures/gui/seasonal_adventures_config_button.png"), 32, 64, (button -> {
            MinecraftClient.getInstance().setScreen(MidnightConfig.getScreen(this, SeasonalAdventures.MOD_ID));
        }));
    }
}
