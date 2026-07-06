package com.sensors.warehouse.service;

import com.sensors.warehouse.model.SensorType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractUdpListener {

    protected final SensorMessageHandler messageHandler;

    protected abstract int getPort();

    protected abstract SensorType getSensorType();

    @PostConstruct
    public void start() {
        log.info("Starting UDP listener for {} on port {}", getSensorType(), getPort());

        new Thread(this::listen).start();
    }

    private void listen() {
        try (DatagramSocket socket = new DatagramSocket(getPort())) {
            log.info("UDP listener started on port {} for {}", getPort(), getSensorType());
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength()).trim();
                log.info("Received UDP on port {}: {}", getPort(), message);
                messageHandler.processMessage(message, getSensorType());
            }
        } catch (Exception e) {
            log.error("Error receiving UDP on port {}", getPort(), e);
        }
    }
}
