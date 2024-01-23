package com.i19.websearcher.service;

import com.i19.websearcher.config.EbayApiConfig;
import com.i19.websearcher.dto.EbayTokenResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EbayApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EbayApiConfig ebayApiConfig;

    @InjectMocks
    private EbayApiService ebayApiService;

    @Test
    void testGetNewAccessToken() {

        // Test data
        String mockAuthCode = "encodedAuthCode";
        String mockClientId = "mockClientId";
        String mockClientSecret = "mockClientSecret";
        String mockRuName = "mockRuName";

        // Mock returns an object answer
        EbayTokenResponse mockResponse = new EbayTokenResponse();
        mockResponse.setAccessToken("testToken");
        mockResponse.setExpiresIn(7200);
        mockResponse.setTokenType("Bearer");

        // Mock settings
        when(ebayApiConfig.getAuthCode()).thenReturn(mockAuthCode);
        when(ebayApiConfig.getClientId()).thenReturn(mockClientId);
        when(ebayApiConfig.getClientSecret()).thenReturn(mockClientSecret);
        when(ebayApiConfig.getRuName()).thenReturn(mockRuName);

        // Setting RestTemplate mock
        when(restTemplate.exchange(
                any(String.class),
                any(),
                any(),
                any(Class.class)))
                .thenReturn(ResponseEntity.ok(mockResponse)
        );

        EbayTokenResponse response = ebayApiService.getNewAccessToken();

        assertNotNull(response);
        assertEquals("testToken", response.getAccessToken());
        assertEquals(7200, response.getExpiresIn());
    }
}
