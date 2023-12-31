package com.i19.websearcher.controller;

import com.i19.websearcher.model.Product;
import com.i19.websearcher.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    private final SearchService searchService;

    @Autowired
    public MainController(SearchService searchService) {
        this.searchService = searchService;
    }

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
        List<Product> products;

        if(sort != null) {
            products = searchService.performSearchAndSort(query, sort, ascending);
        } else {
            products = searchService.performSearch(query);
        }

        model.addAttribute("query", query);
        model.addAttribute("products", products);
        model.addAttribute("total", products.size());
        return "searchResults";
    }

}
