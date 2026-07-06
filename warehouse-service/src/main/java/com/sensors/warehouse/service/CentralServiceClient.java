package com.sensors.warehouse.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensors.warehouse.config.WarehouseProperties;
import com.sensors.warehouse.model.SensorMeasurement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class CentralServiceClient {

    private static final String CENTRAL_SERVICE_ENDPOINT = "/api/metrics";

    private final ObjectMapper objectMapper;
    private final WarehouseProperties warehouseProperties;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void send(SensorMeasurement measurement) {
        try {
            String json = objectMapper.writeValueAsString(measurement);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(warehouseProperties.getCentralServiceUrl() + CENTRAL_SERVICE_ENDPOINT))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            log.info("Sent measurement to central service: {}", measurement);
        } catch (Exception e) {
            log.error("Error sending measurement to central service", e);
        }
    }
}
