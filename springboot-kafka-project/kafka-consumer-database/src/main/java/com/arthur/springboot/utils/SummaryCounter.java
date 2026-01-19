package com.arthur.springboot.utils;

import lombok.Getter;

@Getter
public class SummaryCounter {
    private long botCount;
    private long humanCount;
    public void incrementBot() {
        botCount++;
    }
    public  void incrementHuman() {
        humanCount++;
    }
}
