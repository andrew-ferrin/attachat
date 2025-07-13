package dev.andrew.attachat.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("llm")
public record LlmProperties(String model, String apiKey) {
}
