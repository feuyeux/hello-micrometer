package com.example.hellomicrometer.api;

import io.micrometer.core.instrument.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HelloController {
  private final Counter helloCounter;
  private final Timer helloTimer;
  private final AtomicInteger helloGauge = new AtomicInteger();

  private final DistributionSummary summary;

  public HelloController(MeterRegistry registry) {
    this.helloCounter =
        Counter.builder("hello.counter").description("Counts hello").register(registry);
    this.helloTimer = Timer.builder("hello.timer").description("Times hello").register(registry);
    Gauge.builder("hello.gauge", helloGauge, AtomicInteger::get)
        .description("Gauges hello")
        .register(registry);
    this.summary =
        DistributionSummary.builder("custom.distribution.summary")
            .description("A custom distribution summary")
            .register(registry);
  }

  @GetMapping("/increment")
  public void incrementCounter() {
    helloCounter.increment();
  }

  @GetMapping("/time")
  public void timeSomething() {
    helloTimer.record(
        () -> {
          // perform task to be timed
          try {
            TimeUnit.MILLISECONDS.sleep(100);
          } catch (InterruptedException e) {
            log.error("", e);
          }
        });
  }

  @GetMapping("/gauge")
  public void setGauge(@RequestParam int value) {
    helloGauge.set(value);
  }

  @GetMapping("/record")
  public void record(@RequestParam double amount) {
    summary.record(amount);
  }
}
