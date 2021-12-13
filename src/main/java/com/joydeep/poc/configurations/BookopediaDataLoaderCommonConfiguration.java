package com.joydeep.poc.configurations;

import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.file.Path;

import static com.joydeep.poc.utils.BookUtils.OPEN_LIBRARY_SEARCH_BASE_URI;


@Configuration
public class BookopediaDataLoaderCommonConfiguration {

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraConnectionConfiguration dataStaxAstraConnectionConfiguration) {
        Path bundle = dataStaxAstraConnectionConfiguration.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .baseUrl(OPEN_LIBRARY_SEARCH_BASE_URI).
                        build();
    }

}
