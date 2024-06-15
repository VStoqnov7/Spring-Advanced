package com.example.mobilele;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class MobiLeLeLeWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobiLeLeLeWebApplication.class, args);
    }

}
