package com.i19.websearcher.controller;

import com.i19.websearcher.model.Product;
import com.i19.websearcher.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MainApiController {

    private final ProductService productService;

    public MainApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> listProducts() {
        return productService.findAll();
    }
}
