package com.arthur.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

//Currently there is no real configuration. This class is just to enable scheduling in the application.
@EnableScheduling
@Configuration
public class SchedulerConfig {
}
