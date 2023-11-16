package com.i19.websearcher.events;

import com.i19.websearcher.model.CartItem;
import org.springframework.context.ApplicationEvent;

public class CartEvent extends ApplicationEvent {

    private final CartItem cartItem;
    private final String operation;

    public CartEvent(Object source, CartItem cartItem, String operation) {
        super(source);
        this.cartItem = cartItem;
        this.operation = operation;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public String getOperation() {
        return operation;
    }
}
