package com.i19.websearcher.controller;

import com.i19.websearcher.model.CartItem;
import com.i19.websearcher.model.Product;
import com.i19.websearcher.service.CartService;
import com.i19.websearcher.service.ProductService;
import com.i19.websearcher.service.commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam String productId) {
        Product product = productService.findById(productId);
        if (product != null) {
            //cartService.addToCart(product);
            Command addCommand = new AddToCartCommand(cartService, product);
            addCommand.execute();
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        //cartService.removeItemFromCart(cartItemId);
        Command removeCommand = new RemoveItemFromCartCommand(cartService, cartItemId);
        removeCommand.execute();
        return "redirect:/cart";
    }

    @PostMapping("/cart/add-one")
    public String addOneCartItem(@RequestParam Long cartItemId) {
        //cartService.addOneCartItem(cartItemId);
        Command addOneCommand = new AddOneCartItemCommand(cartService, cartItemId);
        addOneCommand.execute();
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove-one")
    public String removeOneFromCart(@RequestParam Long cartItemId) {
        //cartService.removeOneItemFromCart(cartItemId);
        Command removeOneCommand = new RemoveOneItemFromCartCommand(cartService, cartItemId);
        removeOneCommand.execute();
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        BigDecimal totalCost = cartService.calculateTotalCost();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalCost", totalCost);
        return "cart";
    }
}
