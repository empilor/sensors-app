package com.sensors.warehouse.model;

public record SensorMeasurement(
        String sensorId,
        SensorType type,
        double value,
        long timestamp
) {
    @Override
    public String toString() {
        return "SensorMeasurement{id='%s', type=%s, value=%.1f, timestamp=%d}".formatted(sensorId, type, value, timestamp);
    }
}
