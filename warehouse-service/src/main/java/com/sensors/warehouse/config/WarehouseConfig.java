package com.sensors.warehouse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WarehouseConfig {

    @Bean
    @ConfigurationProperties(prefix = "warehouse")
    public WarehouseProperties warehouseProperties() {
        return new WarehouseProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "udp")
    public UdpProperties udpProperties() {
        return new UdpProperties();
    }
}
