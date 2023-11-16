package com.i19.websearcher.listeners;

import com.i19.websearcher.events.CartEvent;
import com.i19.websearcher.model.CartItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CartEventListener implements ApplicationListener<CartEvent> {

    private static final Logger logger = LoggerFactory.getLogger(CartEventListener.class);

    @Override
    public void onApplicationEvent(CartEvent event) {
        CartItem cartItem = event.getCartItem();
        String operation = event.getOperation();

        if("add".equals(operation)) {
            logger.info("User has added an item to cart: {}", cartItem.getProduct().getName());
        } else if("addOne".equals(operation)) {
            logger.info("User has added an additional item to cart: {}", cartItem.getProduct().getName());
        } else if("remove".equals(operation)) {
            logger.info("User has removed all items from cart of kind: {}", cartItem.getProduct().getName());
        } else if("removeOne".equals(operation)) {
            logger.info("User has removed an item from cart: {}", cartItem.getProduct().getName());
        }

    }
}
