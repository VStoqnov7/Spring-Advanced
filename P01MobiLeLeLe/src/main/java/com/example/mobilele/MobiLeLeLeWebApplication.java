package com.example.mobilele;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@SpringBootApplication
@EnableScheduling
public class MobiLeLeLeWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MobiLeLeLeWebApplication.class, args);
    }
}
