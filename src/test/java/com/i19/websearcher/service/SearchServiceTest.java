package com.i19.websearcher.service;

import com.i19.websearcher.model.Product;
import com.i19.websearcher.proxy.CachingProxySearchStrategy;
import com.i19.websearcher.service.strategies.PriceSortStrategy;
import com.i19.websearcher.service.strategies.factories.NameSortStrategyFactory;
import com.i19.websearcher.service.strategies.factories.PriceSortStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private PriceSortStrategy priceSortStrategy;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ProductService productService;
    @Mock
    private TokenService tokenService;
    @Mock
    private CachingProxySearchStrategy cachingProxySearchStrategy;
    @Mock
    private PriceSortStrategyFactory priceSortStrategyFactory;
    @Mock
    private NameSortStrategyFactory nameSortStrategyFactory;
    @InjectMocks
    private SearchService searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPerformSearch() {

        String query = "testQuery";
        //List<Product> unsortedProducts = Arrays.asList();
    }



}
