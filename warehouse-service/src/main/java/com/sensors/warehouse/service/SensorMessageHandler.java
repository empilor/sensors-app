package com.sensors.warehouse.service;

import com.sensors.warehouse.config.SystemTimeSupplier;
import com.sensors.warehouse.model.SensorMeasurement;
import com.sensors.warehouse.model.SensorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorMessageHandler {

    private static final String MESSAGE_DELIMITER = ";";
    private static final String KEY_VALUE_SEPARATOR = "=";
    private static final String SENSOR_ID_KEY = "sensor_id";
    private static final String VALUE_KEY = "value";

    private final CentralServiceClient centralServiceClient;
    private final SystemTimeSupplier timestampSupplier;

    public void processMessage(String message, SensorType sensorType) {
        try {
            SensorMeasurement measurement = parseMessage(message, sensorType);
            log.info("Received sensor measurement: {}", measurement);
            centralServiceClient.send(measurement);
        } catch (Exception e) {
            log.error("Error processing sensor message: {}", message, e);
        }
    }

    private SensorMeasurement parseMessage(String message, SensorType sensorType) {
        String[] parts = message.split(MESSAGE_DELIMITER);
        if (parts.length < 2) {
            log.warn("Invalid message format: {}. Expected: 'sensor_id=<id>; value=<value>'", message);
            throw new IllegalArgumentException("Invalid message format");
        }

        String sensorId = extractValue(parts[0], SENSOR_ID_KEY);
        Double value = extractDoubleValue(parts[1], VALUE_KEY);

        if (sensorId == null || value == null) {
            log.warn("Failed to parse message: {}", message);
            throw new IllegalArgumentException("Failed to parse message");
        }

        return new SensorMeasurement(sensorId, sensorType, value, timestampSupplier.get());
    }

    private String extractValue(String part, String expectedKey) {
        String[] keyValue = part.trim().split(KEY_VALUE_SEPARATOR);
        if (keyValue.length != 2) {
            return null;
        }
        String key = keyValue[0].trim();
        if (!expectedKey.equals(key)) {
            return null;
        }
        return keyValue[1].trim();
    }

    private Double extractDoubleValue(String part, String expectedKey) {
        String[] keyValue = part.trim().split(KEY_VALUE_SEPARATOR);
        if (keyValue.length != 2) {
            return null;
        }
        String key = keyValue[0].trim();
        if (!expectedKey.equals(key)) {
            return null;
        }
        try {
            return Double.parseDouble(keyValue[1].trim());
        } catch (NumberFormatException e) {
            log.warn("Invalid value format: {}", keyValue[1]);
            return null;
        }
    }
}
