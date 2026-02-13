package com.gabrielle.project_ia.Service;

import com.gabrielle.project_ia.Dto.OllamaRequest;
import com.gabrielle.project_ia.Dto.OllamaResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OllamaClient {
    private final WebClient client;
    private final String model;

    public OllamaClient(@Qualifier("ollamaWebClient") WebClient client, @Value("${ai.ollama.model}") String model) {
        this.client = client;
        this.model = model;
    }

    public String generate(String prompt) {
        OllamaRequest request = OllamaRequest.nonStreaming(model, prompt);

        OllamaResponse response = client.post()
                .uri("/api/generate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OllamaResponse.class)
                .block();

        if(response == null || response.response() == null) {
            throw new RuntimeException("Invalid response from Ollama");
        }

        return response.response().trim();
    }
}
