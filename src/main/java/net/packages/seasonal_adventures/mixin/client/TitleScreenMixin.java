package net.packages.seasonal_adventures.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.packages.seasonal_adventures.SeasonalAdventures;
import net.packages.seasonal_adventures.config.ConfigBuilder;
import net.packages.seasonal_adventures.gui.screen.ConfigSettingsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void init(int y, int spacingY, CallbackInfo ci) {
        TexturedButtonWidget seasonalAdventuresConfigButton = new TexturedButtonWidget(this.width / 2 + 100 + 4, y + spacingY, 20, 20, 0, 0, 20,
                new Identifier(SeasonalAdventures.MOD_ID, "textures/gui/seasonal_adventures_config_button.png"), 32, 64, (button -> {
            MinecraftClient.getInstance().setScreen(new ConfigSettingsScreen(Text.literal("Seasonal Adventures ConfigObject"), this));
        }));
        addDrawableChild(seasonalAdventuresConfigButton);
        if(!ConfigBuilder.getDevelopmentStatus()) seasonalAdventuresConfigButton.active = false;
    }
}
