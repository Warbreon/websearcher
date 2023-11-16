package com.i19.websearcher.service.strategies;

import com.i19.websearcher.model.Product;

import java.util.List;

public interface SearchStrategy {
    List<Product> search(String query);
}
