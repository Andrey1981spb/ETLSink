package ru.voskhod.gpparf.integration.infodiode.sink.app.util.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CounterTransferMetrics {
    private MeterRegistry meterRegistry;
    private Counter transferCount;

    private static final String TRANSFER_COUNT_METRIC_NAME = "TRANSFER_COUNT";
    private static final String TRANSFER_COUNT_TAG_KEY = "type";
    private static final String TRANSFER_COUNT_TAG_VALUE = "word";
    private static final String TRANSFER_COUNT_DESCRIPTION = "Число переданных сообщений в контур";


    public CounterTransferMetrics(final MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    private void initCounter() {
        transferCount = Counter.builder(TRANSFER_COUNT_METRIC_NAME)
                .tag(TRANSFER_COUNT_TAG_KEY, TRANSFER_COUNT_TAG_VALUE)
                .description(TRANSFER_COUNT_DESCRIPTION)
                .register(meterRegistry);
    }

    public void incrementTransferCount() {
        transferCount.increment();

    }

}
