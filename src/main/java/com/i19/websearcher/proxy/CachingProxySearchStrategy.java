package com.i19.websearcher.proxy;

import com.i19.websearcher.model.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Service
@Primary
public class CachingProxySearchStrategy {
    private final Map<String, CompletableFuture<List<Product>>> cache = new ConcurrentHashMap<>();

    public CompletableFuture<List<Product>> search(String query, Supplier<CompletableFuture<List<Product>>> supplier) {
        return cache.computeIfAbsent(query, q -> supplier.get());
    }
}
