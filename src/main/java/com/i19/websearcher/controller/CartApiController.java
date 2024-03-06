package com.i19.websearcher.controller;

import com.i19.websearcher.model.CartItem;
import com.i19.websearcher.model.Product;
import com.i19.websearcher.service.CartService;
import com.i19.websearcher.service.ProductService;
import com.i19.websearcher.service.commands.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    private final CartService cartService;
    private final ProductService productService;

    public CartApiController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/add")
    public Map<String, Object> addToCart(@RequestParam String productId) {
        Product product = productService.findById(productId);
        Map<String, Object> response = new HashMap<>();
        if (product != null) {
            Command addCommand = new AddToCartCommand(cartService, product);
            addCommand.execute();
            response.put("success", true);
            response.put("message", "Product added to cart");
        } else {
            response.put("success", false);
            response.put("message", "Product not found");
        }
        return response;
    }

    @PostMapping("/remove")
    public Map<String, Object> removeFromCart(@RequestParam Long cartItemId) {
        Command removeCommand = new RemoveItemFromCartCommand(cartService, cartItemId);
        removeCommand.execute();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Item removed from cart");
        return response;
    }

    @PostMapping("/add-one")
    public Map<String, Object> addOneCartItem(@RequestParam Long cartItemId) {
        Command addOneCommand = new AddOneCartItemCommand(cartService, cartItemId);
        addOneCommand.execute();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "One item added to cart");
        return response;
    }

    @PostMapping("/remove-one")
    public Map<String, Object> removeOneFromCart(@RequestParam Long cartItemId) {
        Command removeOneCommand = new RemoveOneItemFromCartCommand(cartService, cartItemId);
        removeOneCommand.execute();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "One item removed from cart");
        return response;
    }

    @GetMapping("/")
    public Map<String, Object> showCart() {
        List<CartItem> cartItems = cartService.getCartItems();
        BigDecimal totalCost = cartService.calculateTotalCost();
        Map<String, Object> response = new HashMap<>();
        response.put("cartItems", cartItems);
        response.put("totalCost", totalCost);
        return response;
    }
}

