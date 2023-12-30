package com.i19.websearcher.service;

import com.i19.websearcher.model.Product;
import com.i19.websearcher.proxy.CachingProxySearchStrategy;
import com.i19.websearcher.service.strategies.*;
import com.i19.websearcher.service.strategies.factories.NameSortStrategyFactory;
import com.i19.websearcher.service.strategies.factories.PriceSortStrategyFactory;
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
    private final CachingProxySearchStrategy cachingProxySearchStrategy;
    private final PriceSortStrategyFactory priceSortStrategyFactory;
    private final NameSortStrategyFactory nameSortStrategyFactory;

    @Autowired
    public SearchService(SearchStrategy searchStrategy, PriceSortStrategy priceSortStrategy,
                         RestTemplate restTemplate, ProductService productService,
                         CachingProxySearchStrategy cachingProxySearchStrategy,
                         PriceSortStrategyFactory priceSortStrategyFactory, NameSortStrategyFactory nameSortStrategyFactory) {
        this.searchStrategy = searchStrategy;
        this.priceSortStrategy = priceSortStrategy;
        this.restTemplate = restTemplate;
        this.productService = productService;
        this.cachingProxySearchStrategy = cachingProxySearchStrategy;
        this.priceSortStrategyFactory = priceSortStrategyFactory;
        this.nameSortStrategyFactory = nameSortStrategyFactory;
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public List<Product> performSearchAdapter(String query) {
        this.setSearchStrategy(new EbaySearchStrategy(restTemplate, productService));
        List<Product> ebayResults = searchStrategy.search(query);

        this.setSearchStrategy(new AmazonSearchStrategy());
        List<Product> amazonResults = searchStrategy.search(query);

        List<Product> combinedResults = new ArrayList<>();
        combinedResults.addAll(ebayResults);
        combinedResults.addAll(amazonResults);

        return combinedResults;
    }

    public List<Product> performSearch(String query) {
        return cachingProxySearchStrategy.search(query);
    }

    public List<Product> performSearchAndSort(String query, String sort, boolean ascending) {
        List<Product> products = cachingProxySearchStrategy.search(query);

        SortStrategy sortStrategy = getSortStrategy(sort);
        if (sortStrategy != null) {
            products = sortStrategy.sort(products, ascending);
        }
        return products;
    }

    public SortStrategy getSortStrategy(String type) {
        if("price".equals(type)) {
            return priceSortStrategyFactory.createSortStrategy();
        } else if ("name".equals(type)) {
            return nameSortStrategyFactory.createSortStrategy();
        }
        return null;
    }
}
