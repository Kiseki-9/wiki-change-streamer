  package com.arthur.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.arthur.springboot.repository")
public class SpringbootConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootConsumerApp.class);
    }
}
