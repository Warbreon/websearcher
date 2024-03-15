package com.i19.websearcher.service.strategies;

import com.i19.websearcher.model.Product;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AmazonSearchStrategy implements SearchStrategy {
    @Override
    @Async
    public CompletableFuture<List<Product>> search(String query) {

        return CompletableFuture.completedFuture(new ArrayList<>());
    }
}
