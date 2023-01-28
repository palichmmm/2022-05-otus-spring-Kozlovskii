package ru.otus.spring.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import ru.otus.spring.integration.service.LettersService;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    private final LettersService lettersService;

    public IntegrationConfig(LettersService lettersService) {
        this.lettersService = lettersService;
    }

    @Bean
    public MessageChannel authorChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel genreChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel bookChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow authorFlow() {
        return flow -> flow.handle(lettersService, "authorReplacementLetters");
    }

    @Bean
    public IntegrationFlow genreFlow() {
        return flow -> flow.handle(lettersService, "genreReplacementLetters");
    }

    @Bean
    public IntegrationFlow bookFlow() {
        return flow -> flow.handle(lettersService, "bookReplacementLetters");
    }
}
