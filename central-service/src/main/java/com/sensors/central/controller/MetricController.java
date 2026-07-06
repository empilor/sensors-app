package com.sensors.central.controller;

import com.sensors.central.model.MetricDto;
import com.sensors.central.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
@Slf4j
public class MetricController {

    private final AlarmService alarmService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void receiveMetric(@RequestBody MetricDto metricDto) {
        log.info("Received metric: {}", metricDto);

        alarmService.checkThreshold(metricDto);
    }
}
