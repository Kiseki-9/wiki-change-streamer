package com.arthur.springboot.utils;

import com.arthur.springboot.service.SummaryDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SummaryFlushJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(SummaryFlushJob.class);
    private final SummaryAccumulator summaryAccumulator;
    private final SummaryDataService summaryDataService;

    public SummaryFlushJob(SummaryAccumulator summaryAccumulator, SummaryDataService summaryDataService) {
        this.summaryAccumulator = summaryAccumulator;
        this.summaryDataService = summaryDataService;
    }

    @Scheduled(fixedRateString = "${summary.flush.interval.ms:10000}")
    public void flushToDatabase() {
        Map<String, SummaryCounter> snapshot = summaryAccumulator.drain();
        if (snapshot.isEmpty()){
            LOGGER.info("No summary data to flush to database.");
            return;
        }

        snapshot.forEach((category, counter) -> {
            summaryDataService.upsertSummary(category, counter.getBotCount(), counter.getHumanCount());
        });
        LOGGER.info("Flushed summary data to database: " + snapshot.toString());
    }
}
