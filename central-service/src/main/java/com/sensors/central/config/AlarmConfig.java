package com.sensors.central.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlarmConfig {

    @Bean
    @ConfigurationProperties(prefix = "threshold")
    public ThresholdProperties thresholdProperties() {
        return new ThresholdProperties();
    }
}
