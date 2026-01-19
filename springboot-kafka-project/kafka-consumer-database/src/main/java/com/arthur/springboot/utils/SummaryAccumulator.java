package com.arthur.springboot.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SummaryAccumulator {
    private final ConcurrentHashMap<String, SummaryCounter> buffer = new ConcurrentHashMap<>();

    //Function to accumulate counts based on category and isBot flag in thread-safe manner in regular intervals
    public void accumulate(String category, boolean isBot) {
        buffer.compute(category, (key, counter) -> {
            if (counter == null) {
                counter = new SummaryCounter();
            }
            if (isBot) {
                counter.incrementBot();
            } else {
                counter.incrementHuman();
            }
            return counter;
        });
    }

    //Function to drain the buffer and return a snapshot of current counts, then clear the buffer. This triggers after each interval
    public Map<String,SummaryCounter> drain(){
        HashMap<String, SummaryCounter> snapshot = new HashMap<>(buffer);
        buffer.clear();
        return snapshot;
    }
}
