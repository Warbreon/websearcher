package com.i19.websearcher.service.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i19.websearcher.model.ItemSummaries;
import com.i19.websearcher.model.Price;
import com.i19.websearcher.model.Product;
import com.i19.websearcher.service.ProductService;
import com.i19.websearcher.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class EbaySearchStrategy implements SearchStrategy {

    private final RestTemplate restTemplate;
    private final ProductService productService;
    private final TokenService tokenService;
    private static final String EBAY_ENDPOINT = "https://api.sandbox.ebay.com/buy/browse/v1/item_summary/search";

    @Override
    @Async
    public CompletableFuture<List<Product>> search(String query) {
        return CompletableFuture.supplyAsync(() -> {
            if (query == null || query.trim().isEmpty()) {
                log.error("Search query is empty");
                return Collections.<Product>emptyList();
            }
            try {
            String sandboxToken = tokenService.getCurrentAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(sandboxToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(EBAY_ENDPOINT)
                    .queryParam("q", query);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            ObjectMapper objectMapper = new ObjectMapper();

                ItemSummaries summaries = objectMapper.readValue(response.getBody(), ItemSummaries.class);
                List<Product> products = summaries.getItems();
                if (products != null) {
                    products.forEach(product -> {
                        if (product.getPrice() != null && product.getPrice().getValue() != null) {
                            productService.saveProduct(product);
                        } else {
                            log.warn("Product without price: " + product.getName());
                            Price defaultPrice = new Price();
                            defaultPrice.setValue("0");
                            defaultPrice.setCurrency("USD");
                            product.setPrice(defaultPrice);

                            productService.saveProduct(product);
                        }
                    });
                } else {
                    log.info("No products found of {} kind were found", query);
                }
                return products != null ? products : Collections.<Product>emptyList();
            } catch (Exception e) {
                log.error("Error processing the search results: " + e.getMessage());
                return Collections.<Product>emptyList();
            }
        }).exceptionally(ex -> {
           log.error("Error processing the search results: " + ex.getMessage());
           return Collections.emptyList();
        });

    }
}
