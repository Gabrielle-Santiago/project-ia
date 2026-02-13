package com.gabrielle.project_ia.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class OllamaConfig {

    @Bean
    public WebClient ollamaWebClient(
            @Value("${ai.ollama.url}") String baseUrl,
            @Value("${ai.ollama.timeout}") Duration timeout
    ) {

        HttpClient httpClient = HttpClient.create().responseTimeout(timeout);

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
