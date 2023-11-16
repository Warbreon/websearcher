package com.i19.websearcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    //@Bean anotacija jau sukuria RestTemplate kaip singleton
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
