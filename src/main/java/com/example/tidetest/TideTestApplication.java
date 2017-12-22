package com.example.tidetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TideTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TideTestApplication.class, args);
    }
}
