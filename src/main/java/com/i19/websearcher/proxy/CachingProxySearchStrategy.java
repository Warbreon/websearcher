//package com.i19.websearcher.proxy;
//
//import com.i19.websearcher.model.Product;
//import com.i19.websearcher.service.strategies.SearchStrategy;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class CachingProxySearchStrategy implements SearchStrategy {
//
//    private final SearchStrategy searchStrategy;
//    private final Map<String, List<Product>> cache = new HashMap<>();
//
//    public CachingProxySearchStrategy(SearchStrategy searchStrategy) {
//        this.searchStrategy = searchStrategy;
//    }
//
//    @Override
//    public List<Product> search(String query) {
//        if (cache.containsKey(query)) {
//            return cache.get(query);
//        }
//        List<Product> results = searchStrategy.search(query);
//        cache.put(query, results);
//        return results;
//    }
//}
