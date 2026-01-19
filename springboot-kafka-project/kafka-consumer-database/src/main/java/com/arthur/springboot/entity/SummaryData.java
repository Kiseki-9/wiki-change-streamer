package com.arthur.springboot.entity;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "category"))
public class SummaryData {
    @Id
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Long bot = 0L;
    @Column(nullable = false)
    private Long human = 0L;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getBot() {
        return bot;
    }

    public void setBot(Long bot) {
        this.bot = bot;
    }

    public Long getHuman() {
        return human;
    }

    public void setHuman(Long human) {
        this.human = human;
    }
}
