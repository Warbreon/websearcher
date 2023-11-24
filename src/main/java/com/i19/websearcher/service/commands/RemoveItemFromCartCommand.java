package com.i19.websearcher.service.commands;

import com.i19.websearcher.service.CartService;

public class RemoveItemFromCartCommand implements Command {

    private CartService cartService;
    private Long cartItemId;

    public RemoveItemFromCartCommand(CartService cartService, Long cartItemId) {
        this.cartService = cartService;
        this.cartItemId = cartItemId;
    }

    @Override
    public void execute() {
        cartService.removeItemFromCart(cartItemId);
    }
}
