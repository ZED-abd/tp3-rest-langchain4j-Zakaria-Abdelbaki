package com.zakariaabdelbaki.tp3.llm;

import com.google.genai.Client;
import com.google.genai.types.HttpOptions;
import com.google.genai.types.HttpRetryOptions;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LlmClient {

    private static final String MODELE = "gemini-3.5-flash-lite";

    private Client gemini;
    private GuideTouristique assistant;

    @PostConstruct
    void initialiser() {
        String cle = System.getenv("GEMINI_KEY");
        if (cle == null || cle.isBlank()) {
            throw new IllegalStateException("Clé Gemini absente de l’environnement du serveur.");
        }

        // Une seule tentative, côté SDK Google comme côté LangChain4j.
        HttpOptions options = HttpOptions.builder()
                .timeout(60_000)
                .retryOptions(HttpRetryOptions.builder().attempts(1))
                .build();
        gemini = Client.builder().apiKey(cle).httpOptions(options).build();
        var modele = GoogleGenAiChatModel.builder()
                .client(gemini)
                .modelName(MODELE)
                .responseFormat(ResponseFormat.JSON)
                .maxRetries(0)
                .build();
        assistant = AiServices.create(GuideTouristique.class, modele);
    }

    public String guide(int nb, String lieu) {
        return assistant.guide(nb, lieu);
    }

    @PreDestroy
    void fermer() {
        gemini.close();
    }
}
