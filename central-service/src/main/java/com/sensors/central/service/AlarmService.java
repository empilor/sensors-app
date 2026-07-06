package com.sensors.central.service;

import com.sensors.central.model.MetricDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

    private final List<BaseThresholdChecker> checkers;

    public void checkThreshold(MetricDto metricDto) {
        BaseThresholdChecker checker = checkers.stream()
                .filter(c -> c.getType() == metricDto.type())
                .findFirst()
                .orElse(null);

        if (checker == null) {
            log.warn("Unknown metric type: {}", metricDto.type());
            return;
        }

        if (checker.isThresholdExceeded(metricDto)) {
            String message = buildAlarmMessage(metricDto, checker.getThreshold());
            log.error(message);
            System.err.println(message);
        }
    }

    private String buildAlarmMessage(MetricDto metricDto, double threshold) {
        return """
                
                ========================================
                ALARM: Exceeded threshold %.1f for metric type %s - current value is %.1f
                ========================================""".formatted(threshold, metricDto.type(), metricDto.value());
    }
}
