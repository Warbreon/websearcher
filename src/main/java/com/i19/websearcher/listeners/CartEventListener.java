package com.i19.websearcher.listeners;

import com.i19.websearcher.events.CartEvent;
import com.i19.websearcher.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CartEventListener implements ApplicationListener<CartEvent> {

    @Override
    public void onApplicationEvent(CartEvent event) {
        CartItem cartItem = event.getCartItem();
        String operation = event.getOperation();

        if("add".equals(operation)) {
            log.info("User has added an item to cart: {}", cartItem.getProduct().getName());
        } else if("addOne".equals(operation)) {
            log.info("User has added an additional item to cart: {}", cartItem.getProduct().getName());
        } else if("remove".equals(operation)) {
            log.info("User has removed all items from cart of kind: {}", cartItem.getProduct().getName());
        } else if("removeOne".equals(operation)) {
            log.info("User has removed an item from cart: {}", cartItem.getProduct().getName());
        }

    }
}
