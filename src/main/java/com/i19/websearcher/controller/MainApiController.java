package com.i19.websearcher.controller;

import com.i19.websearcher.model.Product;
import com.i19.websearcher.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MainApiController {

    private final SearchService searchService;

    @GetMapping("/")
    public Map<String, String> home() {
        Map<String, String> response = new HashMap<>();
        response.put("title", "Web Searcher");
        return response;
    }

    @GetMapping("/search")
    public Map<String, Object> search(@RequestParam String query,
                         @RequestParam(required = false) String sort,
                         @RequestParam(required = false) String direction
                         ) {
        boolean ascending = "asc".equals(direction);
        CompletableFuture<List<Product>> futureProducts;

        if(sort != null) {
            futureProducts = searchService.performSearchAndSort(query, sort, ascending);
        } else {
            futureProducts = searchService.performSearchAdapter(query);
        }

        List<Product> products = futureProducts.join();

        Map<String, Object> response = new HashMap<>();
        response.put("query", query);
        response.put("products", products);
        response.put("total", products.size());
        return response;
    }
}
