package com.arthur.springboot.consumer;

import com.arthur.springboot.entity.ApiResponse;
import com.arthur.springboot.entity.WikimediaData;
import com.arthur.springboot.repository.SummaryDataRepository;
import com.arthur.springboot.repository.WikimediaDataRepository;
import com.arthur.springboot.service.SummaryDataService;
import com.arthur.springboot.utils.SummaryAccumulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    private final SummaryAccumulator accumulator;

    public KafkaDatabaseConsumer(SummaryAccumulator accumulator)
    {
        this.accumulator = accumulator;
    }

    @KafkaListener(
            topics = "wikimedia_recentchange",
            groupId = "wikimedia-group"
    )
    public void consume(ApiResponse message) {
        LOGGER.debug(String.format("Message received and added to accumulator -> %s", message.toString()));
        accumulator.accumulate(message.getType(), message.isBot());
    }
}
