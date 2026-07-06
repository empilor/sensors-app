package com.sensors.central.service;

import com.sensors.central.config.ThresholdProperties;
import com.sensors.central.model.MetricType;
import org.springframework.stereotype.Component;

@Component
public class HumidityThresholdChecker extends BaseThresholdChecker {

    public HumidityThresholdChecker(ThresholdProperties properties) {
        super(properties);
    }

    @Override
    public MetricType getType() {
        return MetricType.HUMIDITY;
    }

    @Override
    public double getThreshold() {
        return properties.getHumidity();
    }
}
