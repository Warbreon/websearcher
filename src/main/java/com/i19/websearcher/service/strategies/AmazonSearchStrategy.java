package com.i19.websearcher.service.strategies;

import com.i19.websearcher.model.Product;

import java.util.ArrayList;
import java.util.List;

public class AmazonSearchStrategy implements SearchStrategy {
    @Override
    public List<Product> search(String query) {

        return new ArrayList<>();
    }
}
