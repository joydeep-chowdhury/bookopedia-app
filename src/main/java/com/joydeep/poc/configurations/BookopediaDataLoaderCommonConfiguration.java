package com.joydeep.poc.configurations;

import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;


@Configuration
public class BookopediaDataLoaderCommonConfiguration {

    @Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraConnectionConfiguration dataStaxAstraConnectionConfiguration) {
        Path bundle = dataStaxAstraConnectionConfiguration.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }

}
