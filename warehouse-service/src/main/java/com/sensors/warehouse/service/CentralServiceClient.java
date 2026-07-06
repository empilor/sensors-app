package com.sensors.warehouse.service;

import com.sensors.warehouse.config.WarehouseProperties;
import com.sensors.warehouse.model.SensorMeasurement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class CentralServiceClient {

    private static final String CENTRAL_SERVICE_ENDPOINT = "/api/metrics";

    private final WarehouseProperties warehouseProperties;
    private final RestClient restClient;

    public void send(SensorMeasurement measurement) {
        try {
            restClient.post()
                    .uri(warehouseProperties.getCentralServiceUrl() + CENTRAL_SERVICE_ENDPOINT)
                    .body(measurement)
                    .retrieve()
                    .toBodilessEntity();

            log.info("Sent measurement to central service: {}", measurement);
        } catch (Exception e) {
            log.error("Error sending measurement to central service", e);
        }
    }
}
