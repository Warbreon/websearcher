package com.i19.websearcher.service.strategies;

import com.i19.websearcher.model.Product;
import com.i19.websearcher.service.ProductService;
import com.i19.websearcher.service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EbaySearchStrategyTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ProductService productService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private EbaySearchStrategy ebaySearchStrategy;

    @Test
    void testSearchWithValidResponse() {

        String query = "PC";
        String mockAccessToken = "mockAccessToken";

        String mockResponseBody = "{\"itemSummaries\": [{\"title\": \"Gaming PC\", \"price\": {\"value\": \"1000\", \"currency\": \"USD\"}, \"itemId\": \"someId\", \"itemHref\": \"someHref\"}]}";

        when(tokenService.getCurrentAccessToken()).thenReturn(mockAccessToken);
        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                eq(String.class)))
                .thenReturn(new ResponseEntity<>(mockResponseBody, HttpStatus.OK));

        CompletableFuture<List<Product>> futureResult = ebaySearchStrategy.search(query);
        List<Product> result = futureResult.join();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Gaming PC", result.get(0).getName());
        verify(productService, times(1)).saveProduct(any(Product.class));
    }

    @Test
    void testSearchWithEmptyQuery() {
        String query = "";

        CompletableFuture<List<Product>> futureResult = ebaySearchStrategy.search(query);
        List<Product> result = futureResult.join();

        assertTrue(result.isEmpty());
    }
}
