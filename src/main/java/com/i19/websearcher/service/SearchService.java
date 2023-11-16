package com.i19.websearcher.service;

import com.i19.websearcher.model.Product;
import com.i19.websearcher.service.strategies.AmazonSearchStrategy;
import com.i19.websearcher.service.strategies.EbaySearchStrategy;
import com.i19.websearcher.service.strategies.PriceSortStrategy;
import com.i19.websearcher.service.strategies.SearchStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private SearchStrategy searchStrategy;
    private final PriceSortStrategy priceSortStrategy;
    private final RestTemplate restTemplate;
    private final ProductService productService;

    @Autowired
    public SearchService(SearchStrategy searchStrategy, PriceSortStrategy priceSortStrategy,
                         RestTemplate restTemplate, ProductService productService) {
        this.searchStrategy = searchStrategy;
        this.priceSortStrategy = priceSortStrategy;
        this.restTemplate = restTemplate;
        this.productService = productService;
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

//    public List<Product> performSearch(String query) {
//        this.setSearchStrategy(new EbaySearchStrategy(restTemplate, productService));
//        List<Product> ebayResults = searchStrategy.search(query);
//
//        this.setSearchStrategy(new AmazonSearchStrategy());
//        List<Product> amazonResults = searchStrategy.search(query);
//
//        List<Product> combinedResults = new ArrayList<>();
//        combinedResults.addAll(ebayResults);
//        combinedResults.addAll(amazonResults);
//
//        return combinedResults;
//    }

    public List<Product> performSearch(String query) {
        return searchStrategy.search(query);
    }

    public List<Product> performSearchAndSort(String query, String sort, boolean ascending) {
        List<Product> products = searchStrategy.search(query);
        if ("price".equals(sort)) {
            products = priceSortStrategy.sort(products, ascending);
        }
        return products;
    }
}
