package com.sensors.warehouse;

import com.sensors.warehouse.config.SystemTimeSupplier;
import com.sensors.warehouse.model.SensorMeasurement;
import com.sensors.warehouse.model.SensorType;
import com.sensors.warehouse.service.CentralServiceClient;
import com.sensors.warehouse.service.SensorMessageHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SensorMessageHandlerTest {

    @Autowired
    private SensorMessageHandler messageHandler;

    @MockBean
    private CentralServiceClient centralServiceClient;

    @MockBean
    private SystemTimeSupplier timestampSupplier;

    @Test
    void testProcessValidTemperatureMessage() {
        when(timestampSupplier.get()).thenReturn(1234567890L);

        assertDoesNotThrow(() ->
            messageHandler.processMessage("sensor_id=t1; value=30.0", SensorType.TEMPERATURE)
        );

        ArgumentCaptor<SensorMeasurement> captor = ArgumentCaptor.forClass(SensorMeasurement.class);
        verify(centralServiceClient, times(1)).send(captor.capture());

        SensorMeasurement measurement = captor.getValue();
        assertEquals("t1", measurement.sensorId());
        assertEquals(SensorType.TEMPERATURE, measurement.type());
        assertEquals(30.0, measurement.value());
        assertEquals(1234567890L, measurement.timestamp());
    }

    @Test
    void testProcessValidHumidityMessage() {
        when(timestampSupplier.get()).thenReturn(1234567890L);

        assertDoesNotThrow(() ->
            messageHandler.processMessage("sensor_id=h1; value=45.0", SensorType.HUMIDITY)
        );

        ArgumentCaptor<SensorMeasurement> captor = ArgumentCaptor.forClass(SensorMeasurement.class);
        verify(centralServiceClient, times(1)).send(captor.capture());

        SensorMeasurement measurement = captor.getValue();
        assertEquals("h1", measurement.sensorId());
        assertEquals(SensorType.HUMIDITY, measurement.type());
        assertEquals(45.0, measurement.value());
        assertEquals(1234567890L, measurement.timestamp());
    }

    @Test
    void testProcessInvalidMessage() {
        assertDoesNotThrow(() ->
            messageHandler.processMessage("invalid format", SensorType.TEMPERATURE)
        );

        verify(centralServiceClient, never()).send(any());
    }
}
