package net.packages.seasonal_adventures.config.ai;

import net.fabricmc.loader.api.FabricLoader;
import net.packages.seasonal_adventures.SeasonalAdventures;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;

public class AiPropertiesManager {
    public static String secretKeyFolderPath = FabricLoader.getInstance().getConfigDir().toString() + "\\DO-NOT-SHARE [AI Services secretKey]";
    private static final String API_URL = "https://api.openai.com/v1/models";

    public static void saveSecretKey(String secretKey) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(secretKeyFolderPath + "\\DO-NOT-SHARE.sk"))) {
            writer.write(secretKey);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static String readSecretKey() {
        try (BufferedReader reader = new BufferedReader(new FileReader(secretKeyFolderPath + "\\DO-NOT-SHARE.sk"))) {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isKeyValid(String apiKey) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        }
    }
    public static void initialize() {
        File folder = new File(secretKeyFolderPath);

        if (folder.mkdir()) {
            SeasonalAdventures.LOGGER.info("Created OpenAi configData folder");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(secretKeyFolderPath + "\\READ-ME[ПРОЧТИ-МЕНЯ].txt"))) {
            writer.write("""
                    =====================================================================
                                            !!! WARNING / ПРЕДУПРЕЖДЕНИЕ !!!
                    =====================================================================
                    
                    EN:
                    -----------
                    This file, "DO-NOT-SHARE.sk", contains your confidential OpenAI™ or DeepSeek™ (defined in settings) platform secret configData.
                    This configData is uniquely associated with your account. Unauthorized disclosure or misuse may result in severe security breaches,
                    account compromise.
                    
                    Please refrain from sharing, exposing, or transmitting this configData under any circumstances. Our team will never request that you
                    forward this configData to any third party.
                    
                    RU:
                    -----------
                    Этот файл "DO-NOT-SHARE.sk" содержит ваш конфиденциальный секретный ключ платформы OpenAI™ либо DeepSeek™ (в зависимости от настроек).
                    Данный ключ является уникальным для вашего аккаунта. Несанкционированное раскрытие или использование ключа может привести к
                    серьёзным нарушениям безопасности.
                    
                    Просим вас не передавать, не разглашать и не распространять этот ключ при любых обстоятельствах. Наша команда никогда не будет
                    запрашивать у вас пересылку данного ключа третьим лицам.
                    
                    ---------------------------------------------------------------------
                    By Seasonal Adventures
                    ---------------------------------------------------------------------
                    """
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
