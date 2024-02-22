package com.i19.websearcher.events;

import com.i19.websearcher.model.CartItem;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CartEvent extends ApplicationEvent {

    private final CartItem cartItem;
    private final String operation;

    public CartEvent(Object source, CartItem cartItem, String operation) {
        super(source);
        this.cartItem = cartItem;
        this.operation = operation;
    }

}
