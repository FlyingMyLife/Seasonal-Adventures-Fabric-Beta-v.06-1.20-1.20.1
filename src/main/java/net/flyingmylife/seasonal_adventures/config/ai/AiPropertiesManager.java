package net.flyingmylife.seasonal_adventures.config.ai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.http.Headers;
import com.openai.core.http.HttpResponseFor;
import com.openai.errors.OpenAIException;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseError;
import net.flyingmylife.seasonal_adventures.SA;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

public class AiPropertiesManager {
    public static String secretKeyFolderPath = FabricLoader.getInstance().getConfigDir().toString() + "\\DO-NOT-SHARE [AI Services secretKey]";
    private static final String API_URL = "https://api.openai.com/v1";

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
    public static int checkKey(String apiKey) {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .baseUrl(API_URL)
                .build();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage("\"This is a test.\"")
                .model(ChatModel.GPT_4O_MINI)
                .build();
        try {
            HttpResponseFor<ChatCompletion> chatCompletion = client.chat().completions().withRawResponse().create(params);

            int statusCode = chatCompletion.statusCode();
            Headers headers = chatCompletion.headers();
            return 0;
        } catch (OpenAIException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static void initialize() {
        File folder = new File(secretKeyFolderPath);

        if (folder.mkdir()) {
            SA.LOGGER.info("Created OpenAi configData folder");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(secretKeyFolderPath + "\\READ-ME[ПРОЧТИ-МЕНЯ].txt"))) {
            writer.write("""
                    =====================================================================
                                       !!! WARNING / ПРЕДУПРЕЖДЕНИЕ !!!
                    =====================================================================
                    
                    EN:
                    -----------
                    This file, "DO-NOT-SHARE.sk", contains your confidential OpenAI™ or DeepSeek™ (defined in settings) platform secret key.
                    This key is unique.
                    
                    Please refrain from sharing, exposing, or transmitting these files under any circumstances. Our team will never request to forward it
                    to any third paries.
                    
                    RU:
                    -----------
                    Этот файл "DO-NOT-SHARE.sk" содержит ваш конфиденциальный секретный ключ платформы OpenAI™ либо DeepSeek™ (в зависимости от настроек).
                    Данный ключ является уникальным.
                    
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