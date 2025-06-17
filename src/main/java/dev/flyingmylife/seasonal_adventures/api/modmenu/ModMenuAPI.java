package dev.flyingmylife.seasonal_adventures.api.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.flyingmylife.seasonal_adventures.gui.screen.SAConfigScreen;
import net.minecraft.text.Text;

public class ModMenuAPI implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new SAConfigScreen(Text.of("Настройки Seasonal Adventures"));
    }
}
