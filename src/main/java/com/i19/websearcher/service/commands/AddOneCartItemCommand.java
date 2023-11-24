package com.i19.websearcher.service.commands;

import com.i19.websearcher.service.CartService;

public class AddOneCartItemCommand implements Command {

    private CartService cartService;
    private Long cartItemId;

    public AddOneCartItemCommand(CartService cartService, Long cartItemId) {
        this.cartService = cartService;
        this.cartItemId = cartItemId;
    }

    @Override
    public void execute() {
        cartService.addOneCartItem(cartItemId);
    }
}
