package com.arthur.springboot.service;

import com.arthur.springboot.repository.SummaryDataRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.arthur.springboot.entity.SummaryData;

@Service
public class SummaryDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SummaryDataService.class);
    private final SummaryDataRepository summaryDataRepository;
    public SummaryDataService(SummaryDataRepository summaryDataRepository) {
        this.summaryDataRepository = summaryDataRepository;
    }

    public void saveSummaryData(String category, boolean isBot)
    {
        //check if categry exists first
        Optional<SummaryData> summaryDataOpt = summaryDataRepository.findByCategory(category);
        SummaryData summaryData;
        if (summaryDataOpt.isEmpty())
        {
            summaryData = new SummaryData();
            summaryData.setCategory(category);
        }
        else
            summaryData = summaryDataOpt.get();
        if(isBot)
            summaryData.setBot(summaryData.getBot() + 1);
        else
            summaryData.setHuman(summaryData.getHuman() + 1);
        LOGGER.info("Saving summary data: " + summaryData.toString());
        summaryDataRepository.save(summaryData);
    }

    @Transactional
    public void upsertSummary(String category, long botCount, long humanCount) {
        summaryDataRepository.incrementCounts(category, botCount, humanCount);
    }
}
