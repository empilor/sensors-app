package com.sensors.central.service;

import com.sensors.central.model.MetricDto;
import com.sensors.central.model.MetricType;

public interface ThresholdChecker {

    MetricType getType();

    boolean isThresholdExceeded(MetricDto metricDto);
}
