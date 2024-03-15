package com.i19.websearcher.service.commands;

import com.i19.websearcher.service.CartService;
import com.i19.websearcher.model.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddToCartCommand implements Command {

    private final CartService cartService;
    private final Product product;

    @Override
    public void execute() {
        cartService.addToCart(product);
    }
}
