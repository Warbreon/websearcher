package com.i19.websearcher.controller;

import com.i19.websearcher.model.Product;
import com.i19.websearcher.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@AllArgsConstructor
public class MainController {

    private final SearchService searchService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Web Searcher");
        return "home";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query,
                         @RequestParam(required = false) String sort,
                         @RequestParam(required = false) String direction,
                         Model model){
        boolean ascending = "asc".equals(direction);
        CompletableFuture<List<Product>> futureProducts;

        if(sort != null) {
            futureProducts = searchService.performSearchAndSort(query, sort, ascending);
        } else {
            futureProducts = searchService.performSearchAdapter(query);
        }

        List<Product> products = futureProducts.join();

        model.addAttribute("query", query);
        model.addAttribute("products", products);
        model.addAttribute("total", products.size());
        return "searchResults";
    }

}
