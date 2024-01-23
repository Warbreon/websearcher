package com.i19.websearcher.service;

import com.i19.websearcher.config.EbayApiConfig;
import com.i19.websearcher.dto.EbayTokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
        log.info("Processing: refreshToken");

        String credentials = ebayApiConfig.getClientId() + ":" + ebayApiConfig.getClientSecret();
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("refresh_token", currentRefreshToken);
        requestBody.add("scope", ebayApiConfig.getScope());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<EbayTokenResponse> response = restTemplate.exchange(
                REFRESH_TOKEN_URL,
                HttpMethod.POST,
                request,
                EbayTokenResponse.class
        );

        if(response.getStatusCode() == HttpStatus.OK) {
            log.info("Token successfully refreshed. Access token: {}", response.getBody().getAccessToken());
            return response.getBody();
        } else {
            log.error("Failed to refresh token. Status code: {}, Response body: {}", response.getStatusCode(), response.getBody());
            throw new RuntimeException("Failed to obtain tokens: " + response.getStatusCode());
        }
    }

    public EbayTokenResponse getNewAccessToken() {
        log.info("Processing: getNewAccessToken");

        String decodedAuthCode = URLDecoder.decode(ebayApiConfig.getAuthCode(), StandardCharsets.UTF_8);
        log.debug("Decoded auth code: {}", decodedAuthCode);

        String credentials = ebayApiConfig.getClientId() + ":" + ebayApiConfig.getClientSecret();
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("code", decodedAuthCode);
        requestBody.add("redirect_uri", ebayApiConfig.getRuName());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<EbayTokenResponse> response = restTemplate.exchange(
                REFRESH_TOKEN_URL,
                HttpMethod.POST,
                request,
                EbayTokenResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Response from eBay API: {}", response.getBody());
        }
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().getAccessToken() != null) {
            log.info("Access token successfully retrieved using authorization code");
            return response.getBody();
        } else {
            log.error("Failed to obtain tokens. Status code: {}, Response body: {}", response.getStatusCode(), response.getBody());
            throw new RuntimeException("Failed to obtain tokens: " + response.getStatusCode() + " Response body: " + response.getBody());
        }
    }
}
