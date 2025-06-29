package net.flyingmylife.seasonal_adventures.mixin.client;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SelectWorldScreen.class)
public class ExperimentWarningFixMixin extends Screen {
    protected ExperimentWarningFixMixin(Text title) {
        super(title);
    }
}
