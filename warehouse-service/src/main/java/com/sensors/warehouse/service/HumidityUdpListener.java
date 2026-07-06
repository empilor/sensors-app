package com.sensors.warehouse.service;

import com.sensors.warehouse.config.UdpProperties;
import com.sensors.warehouse.model.SensorType;
import org.springframework.stereotype.Component;

@Component
public class HumidityUdpListener extends AbstractUdpListener {

    private final UdpProperties udpProperties;

    public HumidityUdpListener(UdpProperties udpProperties, SensorMessageHandler messageHandler) {
        super(messageHandler);
        this.udpProperties = udpProperties;
    }

    @Override
    protected int getPort() {
        return udpProperties.getHumidityPort();
    }

    @Override
    protected SensorType getSensorType() {
        return SensorType.HUMIDITY;
    }
}
