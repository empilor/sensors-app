package com.sensors.central.model;

public record MetricDto(
    String sensorId,
    MetricType type,
    double value,
    long timestamp
) {
    @Override
    public String toString() {
        return "MetricDto{id='%s', type=%s, value=%.1f, timestamp=%d}".formatted(sensorId, type, value, timestamp);
    }
}
