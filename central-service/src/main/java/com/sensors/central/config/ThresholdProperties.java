package com.sensors.central.config;

import lombok.Data;

@Data
public class ThresholdProperties {

    private double temperature = 35.0;
    private double humidity = 50.0;
}
