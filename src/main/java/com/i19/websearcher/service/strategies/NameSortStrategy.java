package com.i19.websearcher.service.strategies;

import com.i19.websearcher.model.Product;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class NameSortStrategy implements SortStrategy{
    @Override
    public List<Product> sort(List<Product> products, boolean ascending) {
        products.sort(Comparator.comparing(Product::getName));

        if(!ascending) {
            Collections.reverse(products);
        }

        return products;
    }
}
