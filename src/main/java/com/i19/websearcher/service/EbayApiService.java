package com.i19.websearcher.service;

import com.i19.websearcher.config.EbayApiConfig;
import com.i19.websearcher.dto.EbayTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;

@Service
@Slf4j
public class EbayApiService {

    private final EbayApiConfig ebayApiConfig;
    private final RestTemplate restTemplate;
    private final String REFRESH_TOKEN_URL = "https://api.sandbox.ebay.com/identity/v1/oauth2/token";

    public EbayApiService(EbayApiConfig ebayApiConfig, RestTemplate restTemplate) {
        this.ebayApiConfig = ebayApiConfig;
        this.restTemplate = restTemplate;
    }

    public EbayTokenResponse refreshToken(String currentRefreshToken) {

        String credentials = ebayApiConfig.getClientId() + ":" + ebayApiConfig.getClientSecret();
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic" + encodedCredentials);

        String requestBody = UriComponentsBuilder.fromHttpUrl("")
                .queryParam("grant_type", "refresh_token")
                .queryParam("refresh_token", currentRefreshToken)
                .toUriString().substring(1);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<EbayTokenResponse> response = restTemplate.exchange(
                REFRESH_TOKEN_URL,
                HttpMethod.POST, request, EbayTokenResponse.class);

        log.info("token successfully refreshed");
        return response.getBody();
    }


}
