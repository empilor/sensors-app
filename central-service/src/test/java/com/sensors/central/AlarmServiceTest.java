package com.sensors.central;

import com.sensors.central.config.ThresholdProperties;
import com.sensors.central.model.MetricDto;
import com.sensors.central.model.MetricType;
import com.sensors.central.service.AlarmService;
import com.sensors.central.service.BaseThresholdChecker;
import com.sensors.central.service.HumidityThresholdChecker;
import com.sensors.central.service.TemperatureThresholdChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(OutputCaptureExtension.class)
class AlarmServiceTest {

    private static final double TEMPERATURE_BELOW_THRESHOLD = 30.0;
    private static final double TEMPERATURE_ABOVE_THRESHOLD = 40.0;
    private static final double HUMIDITY_BELOW_THRESHOLD = 45.0;
    private static final double HUMIDITY_ABOVE_THRESHOLD = 60.0;

    private AlarmService alarmService;
    private ThresholdProperties properties;

    @BeforeEach
    void setUp() {
        properties = new ThresholdProperties();
        List<BaseThresholdChecker> checkers = List.of(
                new TemperatureThresholdChecker(properties),
                new HumidityThresholdChecker(properties)
        );
        alarmService = new AlarmService(checkers);
    }

    @Test
    void testTemperatureBelowThreshold(CapturedOutput output) {
        MetricDto metricDto = new MetricDto("t1", MetricType.TEMPERATURE, TEMPERATURE_BELOW_THRESHOLD, 1234567890L);
        alarmService.checkThreshold(metricDto);

        assertFalse(output.getErr().contains("ALARM"));
    }

    @Test
    void testTemperatureAboveThreshold(CapturedOutput output) {
        MetricDto metricDto = new MetricDto("t1", MetricType.TEMPERATURE, TEMPERATURE_ABOVE_THRESHOLD, 1234567890L);
        alarmService.checkThreshold(metricDto);

        String err = output.getErr();
        assertTrue(err.contains("ALARM"), "Expected ALARM in output: " + err);
        assertTrue(err.contains("TEMPERATURE"), "Expected TEMPERATURE in output: " + err);
    }

    @Test
    void testHumidityBelowThreshold(CapturedOutput output) {
        MetricDto metricDto = new MetricDto("h1", MetricType.HUMIDITY, HUMIDITY_BELOW_THRESHOLD, 1234567890L);
        alarmService.checkThreshold(metricDto);

        assertFalse(output.getErr().contains("ALARM"));
    }

    @Test
    void testHumidityAboveThreshold(CapturedOutput output) {
        MetricDto metricDto = new MetricDto("h1", MetricType.HUMIDITY, HUMIDITY_ABOVE_THRESHOLD, 1234567890L);
        alarmService.checkThreshold(metricDto);

        String err = output.getErr();
        assertTrue(err.contains("ALARM"), "Expected ALARM in output: " + err);
        assertTrue(err.contains("HUMIDITY"), "Expected HUMIDITY in output: " + err);
    }
}
