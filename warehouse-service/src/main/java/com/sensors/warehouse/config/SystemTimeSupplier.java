package com.sensors.warehouse.config;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.function.Supplier;

@Component
public class SystemTimeSupplier implements Supplier<Long> {

    private final Clock clock;

    public SystemTimeSupplier() {
        this.clock = Clock.systemDefaultZone();
    }

    @Override
    public Long get() {
        return clock.millis();
    }
}
