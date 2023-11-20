package com.i19.websearcher.service.strategies.factories;

import com.i19.websearcher.service.strategies.NameSortStrategy;
import com.i19.websearcher.service.strategies.SortStrategy;
import org.springframework.stereotype.Service;

@Service
public class NameSortStrategyFactory implements SortStrategyFactory{
    @Override
    public SortStrategy createSortStrategy() {
        return new NameSortStrategy();
    }
}
