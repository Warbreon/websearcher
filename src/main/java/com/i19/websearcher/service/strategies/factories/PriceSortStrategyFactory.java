package com.i19.websearcher.service.strategies.factories;

import com.i19.websearcher.service.strategies.SortStrategy;
import com.i19.websearcher.service.strategies.PriceSortStrategy;
import org.springframework.stereotype.Service;

@Service
public class PriceSortStrategyFactory implements SortStrategyFactory {
    @Override
    public SortStrategy createSortStrategy() {
        return new PriceSortStrategy();
    }
}
