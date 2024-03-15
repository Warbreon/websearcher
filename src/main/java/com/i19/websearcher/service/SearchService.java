package com.i19.websearcher.service;

import com.i19.websearcher.model.Product;
import com.i19.websearcher.proxy.CachingProxySearchStrategy;
import com.i19.websearcher.service.strategies.*;
import com.i19.websearcher.service.strategies.factories.NameSortStrategyFactory;
import com.i19.websearcher.service.strategies.factories.PriceSortStrategyFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class SearchService {

    @Autowired
    private SearchStrategy ebaySearchStrategy;
    @Autowired
    private SearchStrategy amazonSearchStrategy;
    @Autowired
    private final CachingProxySearchStrategy cachingProxySearchStrategy;
    private final PriceSortStrategyFactory priceSortStrategyFactory;
    private final NameSortStrategyFactory nameSortStrategyFactory;

    public CompletableFuture<List<Product>> performSearchAdapter(String query) {
        return cachingProxySearchStrategy.search(query, () -> {
            CompletableFuture<List<Product>> ebayFuture = ebaySearchStrategy.search(query);
            CompletableFuture<List<Product>> amazonFuture = amazonSearchStrategy.search(query);

            return CompletableFuture.allOf(ebayFuture, amazonFuture)
                    .thenApply(v -> {
                        List<Product> results = new ArrayList<>();
                        results.addAll(ebayFuture.join());
                        results.addAll(amazonFuture.join());
                        return results;
                    });
        });
    }

    public CompletableFuture<List<Product>> performSearchAndSort(String query, String sort, boolean ascending) {
        return performSearchAdapter(query).thenApply(products -> {
            SortStrategy sortStrategy = getSortStrategy(sort);
            if (sortStrategy != null) {
                products = sortStrategy.sort(products, ascending);
            }
            return products;
        });
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
