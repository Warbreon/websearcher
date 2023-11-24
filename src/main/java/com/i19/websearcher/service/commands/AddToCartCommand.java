package com.i19.websearcher.service.commands;

import com.i19.websearcher.service.CartService;
import com.i19.websearcher.model.Product;

public class AddToCartCommand implements Command {

    private CartService cartService;
    private Product product;

    public AddToCartCommand(CartService cartService, Product product) {
        this.cartService = cartService;
        this.product = product;
    }

    @Override
    public void execute() {
        cartService.addToCart(product);
    }
}
