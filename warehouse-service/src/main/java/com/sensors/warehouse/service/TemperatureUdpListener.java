package com.sensors.warehouse.service;

import com.sensors.warehouse.config.UdpProperties;
import com.sensors.warehouse.model.SensorType;
import org.springframework.stereotype.Component;

@Component
public class TemperatureUdpListener extends AbstractUdpListener {

    private final UdpProperties udpProperties;

    public TemperatureUdpListener(UdpProperties udpProperties, SensorMessageHandler messageHandler) {
        super(messageHandler);
        this.udpProperties = udpProperties;
    }

    @Override
    protected int getPort() {
        return udpProperties.getTemperaturePort();
    }

    @Override
    protected SensorType getSensorType() {
        return SensorType.TEMPERATURE;
    }
}
