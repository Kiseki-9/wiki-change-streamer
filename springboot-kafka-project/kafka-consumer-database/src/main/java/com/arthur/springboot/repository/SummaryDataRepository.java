package com.arthur.springboot.repository;

import com.arthur.springboot.entity.SummaryData;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SummaryDataRepository extends JpaRepository<SummaryData,String> {
    Optional<SummaryData> findByCategory(String category);

    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO summary_data (category, bot, human)
    VALUES (:category, :bot, :human)
    ON CONFLICT (category)
    DO UPDATE SET
        bot = summary_data.bot + EXCLUDED.bot,
        human = summary_data.human + EXCLUDED.human
    """, nativeQuery = true)
    int incrementCounts(@Param("category") String category,@Param("bot") long botCount,@Param("human") long humanCount);
}
