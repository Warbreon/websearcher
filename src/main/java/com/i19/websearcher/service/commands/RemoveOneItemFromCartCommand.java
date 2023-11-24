package com.i19.websearcher.service.commands;

import com.i19.websearcher.service.CartService;

public class RemoveOneItemFromCartCommand implements Command {

    private CartService cartService;
    private Long cartItemId;

    public RemoveOneItemFromCartCommand(CartService cartService, Long cartItemId) {
        this.cartService = cartService;
        this.cartItemId = cartItemId;
    }

    @Override
    public void execute() {
        cartService.removeOneItemFromCart(cartItemId);
    }
}
