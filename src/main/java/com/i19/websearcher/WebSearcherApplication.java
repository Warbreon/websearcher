package com.i19.websearcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class WebSearcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSearcherApplication.class, args);
    }

}
