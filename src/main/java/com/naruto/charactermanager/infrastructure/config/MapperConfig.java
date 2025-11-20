package com.naruto.charactermanager.infrastructure.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper mapper() {
        var modelMapper = new ModelMapper();

        modelMapper
                .getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // Configurar conversión de Timestamp a LocalDateTime
        modelMapper.createTypeMap(Timestamp.class, LocalDateTime.class)
                .setConverter(context -> {
                    Timestamp timestamp = context.getSource();
                    return timestamp != null ? timestamp.toLocalDateTime() : null;
                });

        // Configurar conversión de LocalDateTime a Timestamp
        modelMapper.createTypeMap(LocalDateTime.class, Timestamp.class)
                .setConverter(context -> {
                    LocalDateTime localDateTime = context.getSource();
                    return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
                });

        return modelMapper;
    }
}

