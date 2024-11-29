package net.packages.seasonal_adventures.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageManager;

public class ClientUtils {
    @Environment(EnvType.CLIENT)
    public static String getCurrentLanguageCode() {
        MinecraftClient client = MinecraftClient.getInstance();
        LanguageManager languageManager = client.getLanguageManager();
        return languageManager.getLanguage();
    }
}
