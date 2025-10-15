package com.notification_service.notification_service.Config;

import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.ollama.OllamaChatClient;
//import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.ai.ollama.api.OllamaApi;


@Configuration
public class ChatClientConfig {

//    @Value("${spring.ai.ollama.base-url}")
//    private String baseUrl;
//
//    @Value("${spring.ai.ollama.model}")
//    private String model;

    @Bean
    public ChatModel chatModel() {
        OllamaApi api = OllamaApi.builder()
                .baseUrl("http://localhost:11434")
                .build();

        OllamaOptions options = OllamaOptions.builder()
                .model("llama3")
                .build();

        return OllamaChatModel.builder()
                .ollamaApi(api)
                .defaultOptions(options)
                .build();
    }

//        @Bean
//        public ChatClient chatClient() {
//            // Assuming Ollama is running locally
//            OllamaApi ollamaApi = OllamaApi.builder()
//                    .baseUrl("http://localhost:11434") // Ollama running locally
//                    .build();
//            return new OllamaChatClient(ollamaApi)
//                    .withDefaultOptions(options -> options.withModel("llama3"));
//        }
}