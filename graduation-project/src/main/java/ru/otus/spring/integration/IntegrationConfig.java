//package ru.otus.string.integration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.channel.PublishSubscribeChannel;
//import org.springframework.integration.config.EnableIntegration;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.IntegrationFlows;
//import org.springframework.integration.dsl.MessageChannels;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.web.multipart.MultipartFile;
//import ru.otus.string.integration.service.UploadFileService;
//import ru.otus.string.models.File;
//
//@Configuration
//@EnableIntegration
//public class IntegrationConfig {
//
////    private final LettersService lettersService;
////
//    private final UploadFileService fileService;
//
//    public IntegrationConfig(UploadFileService fileService) {
//        this.fileService = fileService;
//    }
//
//    @Bean
//    public MessageChannel uploadChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public PublishSubscribeChannel aggregateFileChannel() {
//        return MessageChannels.publishSubscribe().get();
//    }
//
//    @Bean
//    public PublishSubscribeChannel outputFilesChannel() {
//        return MessageChannels.publishSubscribe().get();
//    }

//    @Bean
//    public MessageChannel genreChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public PublishSubscribeChannel aggregateGenreChannel() {
//        return MessageChannels.publishSubscribe().get();
//    }
//
//    @Bean
//    public PublishSubscribeChannel outputGenreChannel() {
//        return MessageChannels.publishSubscribe().get();
//    }
//
//    @Bean
//    public MessageChannel bookChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public PublishSubscribeChannel aggregateBookChannel() {
//        return MessageChannels.publishSubscribe().get();
//    }
//
//    @Bean
//    public PublishSubscribeChannel outputBookChannel() {
//        return MessageChannels.publishSubscribe().get();
//    }

//    @Bean
//    public IntegrationFlow uploadFlow() {
//        return flow -> flow.channel(uploadChannel())
//                .split()
//                .<MultipartFile, File>route(fileService::authorTaboo,
//                        mapping -> mapping
//                                .subFlowMapping(true, sub -> sub
//                                        .transform(lettersService, "authorReplacementLetters")
//                                        .channel(aggregateFileChannel()))
//                                .subFlowMapping(false, sub -> sub.channel(aggregateFileChannel()))
//                );
//    }
//
//    @Bean
//    public IntegrationFlow aggregateAuthorFlow() {
//        return IntegrationFlows.from(aggregateFilesChannel())
//                .aggregate()
//                .channel(outputFilesChannel())
//                .get();
//    }

//    @Bean
//    public IntegrationFlow genreFlow() {
//        return flow -> flow.channel(genreChannel())
//                .split()
//                .<Genre, Boolean>route(tabooService::genreTaboo,
//                        mapping -> mapping
//                                .subFlowMapping(true, sub -> sub
//                                        .transform(lettersService, "genreReplacementLetters")
//                                        .channel(aggregateGenreChannel()))
//                                .subFlowMapping(false, sub -> sub.channel(aggregateGenreChannel()))
//                );
//    }
//
//    @Bean
//    public IntegrationFlow aggregateGenreFlow() {
//        return IntegrationFlows.from(aggregateGenreChannel())
//                .aggregate()
//                .channel(outputGenreChannel())
//                .get();
//    }
//
//    @Bean
//    public IntegrationFlow bookFlow() {
//        return flow -> flow.channel(bookChannel())
//                .split()
//                .<Book, Boolean>route(tabooService::bookTaboo,
//                        mapping -> mapping
//                                .subFlowMapping(true, sub -> sub
//                                        .transform(lettersService, "bookReplacementLetters")
//                                        .channel(aggregateBookChannel()))
//                                .subFlowMapping(false, sub -> sub.channel(aggregateBookChannel()))
//                );
//    }
//
//    @Bean
//    public IntegrationFlow aggregateBookFlow() {
//        return IntegrationFlows.from(aggregateBookChannel())
//                .aggregate()
//                .channel(outputBookChannel())
//                .get();
//    }
//}
