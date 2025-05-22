package com.app.projetoapi;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ModelMapperConfig {
    private Map<String, Object> changedAttrs = new HashMap<>();
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}