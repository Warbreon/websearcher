package com.i19.websearcher.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class EbayApiConfig {

    @Value("${ebay.api.sandbox.app-id}")
    private String clientId;

    @Value("${ebay.api.sandbox.dev-id}")
    private String devId;

    @Value("${ebay.api.sandbox.cert-id}")
    private String clientSecret;

    @Value("${ebay.api.sandbox.ru-name}")
    private String ruName;

    @Value("${ebay.api.sandbox.scope}")
    private String scope;

    @Value("${ebay.api.sandbox.auth-code}")
    private String authCode;
}
