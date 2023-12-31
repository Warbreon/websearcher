package com.i19.websearcher.service.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.i19.websearcher.model.ItemSummaries;
import com.i19.websearcher.model.Price;
import com.i19.websearcher.model.Product;
import com.i19.websearcher.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class EbaySearchStrategy implements SearchStrategy {

    private static final Logger logger = LoggerFactory.getLogger(EbaySearchStrategy.class);
    private final RestTemplate restTemplate;
    private final ProductService productService;
    private final String ebayEndpoint = "https://api.sandbox.ebay.com/buy/browse/v1/item_summary/search";

    @Value("${ebay.api.sandbox.token}")
    private String sandboxToken;

    public EbaySearchStrategy(RestTemplate restTemplate, ProductService productService) {
        this.restTemplate = restTemplate;
        this.productService = productService;
    }

    @Override
    public List<Product> search(String query) {
        if(query == null || query.trim().isEmpty()){
            logger.error("Search query is empty");
            return Collections.emptyList();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(sandboxToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ebayEndpoint)
                .queryParam("q", query);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ItemSummaries summaries = objectMapper.readValue(response.getBody(), ItemSummaries.class);
            List<Product> products = summaries.getItems();
            if(products != null){
                products.forEach(product -> {
                    if(product.getPrice() != null && product.getPrice().getValue() != null) {
                        productService.saveProduct(product);
                    } else {
                        logger.warn("Product without price: " + product.getName());
                        Price defaultPrice = new Price();
                        defaultPrice.setValue("0");
                        defaultPrice.setCurrency("USD");
                        product.setPrice(defaultPrice);

                        productService.saveProduct(product);
                    }
                });
                return products;
            } else {
                logger.info("No products found of {} kind were found", query);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Error processing the search results: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
