package dev.andrew.attachat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.andrew.attachat.config.properties.LlmProperties;
import io.github.sashirestela.openai.SimpleOpenAI;

@Configuration
public class LlmConfiguration {

    @Bean
    public SimpleOpenAI simpleOpenAI(LlmProperties llmProperties) {
        return SimpleOpenAI.builder()
                .apiKey(llmProperties.apiKey())
                .build();
    }

}
