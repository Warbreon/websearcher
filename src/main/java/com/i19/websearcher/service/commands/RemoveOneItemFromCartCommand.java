package com.i19.websearcher.service.commands;

import com.i19.websearcher.service.CartService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveOneItemFromCartCommand implements Command {

    private final CartService cartService;
    private final Long cartItemId;

    @Override
    public void execute() {
        cartService.removeOneItemFromCart(cartItemId);
    }
}
