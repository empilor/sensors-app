package com.sensors.warehouse.config;

import lombok.Data;

@Data
public class UdpProperties {

    private String name;
    private int temperaturePort;
    private int humidityPort;
}
