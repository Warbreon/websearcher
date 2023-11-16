package com.i19.websearcher.service.strategies;

import com.i19.websearcher.model.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceSortStrategy implements SortStrategy {
    @Override
    public List<Product> sort(List<Product> products, boolean ascending) {
        products.sort(Comparator.comparing(product -> {
            try {
                return new BigDecimal(product.getPrice().getValue());
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        }, Comparator.nullsLast(Comparator.naturalOrder())
        ));

        if(!ascending) {
            Collections.reverse(products);
        }

        return products;
    }
}
