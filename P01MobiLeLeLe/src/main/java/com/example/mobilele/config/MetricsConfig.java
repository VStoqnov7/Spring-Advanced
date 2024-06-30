package com.example.mobilele.config;

import com.example.mobilele.repository.OfferRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    public MetricsConfig(MeterRegistry meterRegistry,
                         OfferRepository offerRepository) {

        Gauge.builder("offer.count", offerRepository::count).
                register(meterRegistry);
    }
}