package com.i19.websearcher.service.strategies;

import com.i19.websearcher.model.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SearchStrategy {
    CompletableFuture<List<Product>> search(String query);
}
