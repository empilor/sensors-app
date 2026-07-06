package com.sensors.central.service;

import com.sensors.central.config.ThresholdProperties;
import com.sensors.central.model.MetricDto;
import com.sensors.central.model.MetricType;

public abstract class BaseThresholdChecker implements ThresholdChecker {

    protected final ThresholdProperties properties;

    protected BaseThresholdChecker(ThresholdProperties properties) {
        this.properties = properties;
    }

    @Override
    public abstract MetricType getType();

    @Override
    public boolean isThresholdExceeded(MetricDto metricDto) {
        return metricDto.value() > getThreshold();
    }

    public abstract double getThreshold();
}
