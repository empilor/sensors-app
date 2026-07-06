package com.sensors.central.service;

import com.sensors.central.config.ThresholdProperties;
import com.sensors.central.model.MetricType;
import org.springframework.stereotype.Component;

@Component
public class TemperatureThresholdChecker extends BaseThresholdChecker {

    public TemperatureThresholdChecker(ThresholdProperties properties) {
        super(properties);
    }

    @Override
    public MetricType getType() {
        return MetricType.TEMPERATURE;
    }

    @Override
    public double getThreshold() {
        return properties.getTemperature();
    }
}
