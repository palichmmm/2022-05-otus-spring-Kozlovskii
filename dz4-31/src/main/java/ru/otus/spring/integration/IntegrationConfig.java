package ru.otus.spring.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import ru.otus.spring.integration.service.LettersService;
import ru.otus.spring.integration.service.TabooService;
import ru.otus.spring.models.Author;
import ru.otus.spring.models.Genre;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    private final LettersService lettersService;

    private final TabooService tabooService;

    public IntegrationConfig(LettersService lettersService, TabooService tabooService) {
        this.lettersService = lettersService;
        this.tabooService = tabooService;
    }

    @Bean
    public MessageChannel authorChannel() {
        return new DirectChannel();
    }

    @Bean
    public PublishSubscribeChannel aggregateAuthorChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel outputAuthorChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public MessageChannel genreChannel() {
        return new DirectChannel();
    }

    @Bean
    public PublishSubscribeChannel aggregateGenreChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel outputGenreChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public MessageChannel bookChannel() {
        return new DirectChannel();
    }

    @Bean
    public PublishSubscribeChannel aggregateBookChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel outputBookChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow authorFlow() {
        return flow -> flow.channel(authorChannel())
                .split()
                .<Author, Boolean>route(tabooService::authorTaboo,
                        mapping -> mapping
                                .subFlowMapping(true, sub -> sub
                                        .transform(lettersService, "authorReplacementLetters")
                                        .channel(aggregateAuthorChannel()))
                                .subFlowMapping(false, sub -> sub.channel(aggregateAuthorChannel()))
                );
    }

    @Bean
    public IntegrationFlow aggregateAuthorFlow() {
        return IntegrationFlows.from(aggregateAuthorChannel())
                .aggregate()
                .channel(outputAuthorChannel())
                .get();
    }

    @Bean
    public IntegrationFlow genreFlow() {
        return flow -> flow.channel(genreChannel())
                .split()
                .<Genre, Boolean>route(tabooService::genreTaboo,
                        mapping -> mapping
                                .subFlowMapping(true, sub -> sub
                                        .transform(lettersService, "genreReplacementLetters")
                                        .channel(aggregateGenreChannel()))
                                .subFlowMapping(false, sub -> sub.channel(aggregateGenreChannel()))
                );
    }

    @Bean
    public IntegrationFlow aggregateGenreFlow() {
        return IntegrationFlows.from(aggregateGenreChannel())
                .aggregate()
                .channel(outputGenreChannel())
                .get();
    }

    @Bean
    public IntegrationFlow bookFlow() {
        return flow -> flow.handle(lettersService, "bookReplacementLetters");
    }
}
