package com.i19.websearcher.service;

import com.i19.websearcher.model.Product;
import com.i19.websearcher.proxy.CachingProxySearchStrategy;
import com.i19.websearcher.service.strategies.*;
import com.i19.websearcher.service.strategies.factories.NameSortStrategyFactory;
import com.i19.websearcher.service.strategies.factories.PriceSortStrategyFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchService {

    private SearchStrategy searchStrategy;
    private final RestTemplate restTemplate;
    private final ProductService productService;
    private final TokenService tokenService;
    private final CachingProxySearchStrategy cachingProxySearchStrategy;
    private final PriceSortStrategyFactory priceSortStrategyFactory;
    private final NameSortStrategyFactory nameSortStrategyFactory;

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public List<Product> performSearchAdapter(String query) {
        this.setSearchStrategy(new EbaySearchStrategy(restTemplate, productService, tokenService));
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
        List<Product> products = performSearch(query);

        SortStrategy sortStrategy = getSortStrategy(sort);
        if (sortStrategy != null) {
            products = sortStrategy.sort(products, ascending);
        }
        return products;
    }

    private SortStrategy getSortStrategy(String type) {
        if("price".equals(type)) {
            return priceSortStrategyFactory.createSortStrategy();
        } else if ("name".equals(type)) {
            return nameSortStrategyFactory.createSortStrategy();
        }
        return null;
    }
}
