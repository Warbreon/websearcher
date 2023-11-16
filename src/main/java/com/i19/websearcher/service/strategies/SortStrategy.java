package com.i19.websearcher.service.strategies;

import com.i19.websearcher.model.Product;

import java.util.List;

public interface SortStrategy {
    List<Product> sort(List<Product> products, boolean ascending);
}
