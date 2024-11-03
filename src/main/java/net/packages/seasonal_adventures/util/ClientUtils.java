package net.packages.seasonal_adventures.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageManager;

public class ClientUtils {
    public static String getCurrentLanguageCode() {
        MinecraftClient client = MinecraftClient.getInstance();
        LanguageManager languageManager = client.getLanguageManager();
        return languageManager.getLanguage();
    }
}
