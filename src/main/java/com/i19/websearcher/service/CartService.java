package com.i19.websearcher.service;

import com.i19.websearcher.events.CartEvent;
import com.i19.websearcher.model.CartItem;
import com.i19.websearcher.model.Product;
import com.i19.websearcher.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public CartService(CartItemRepository cartItemRepository, ApplicationEventPublisher publisher) {
        this.cartItemRepository = cartItemRepository;
        this.publisher = publisher;
    }

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem addToCart(Product product) {
        CartItem cartItem = cartItemRepository.findByProduct(product)
                .orElseGet(() -> {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setProduct(product);
                    newCartItem.setQuantity(0);
                    return newCartItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        publishCartEvent(cartItem, "add");
        return cartItemRepository.save(cartItem);
    }

    public void removeItemFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
        publishCartEvent(cartItem, "remove");
        cartItemRepository.deleteById(cartItemId);
    }

    public void addOneCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        publishCartEvent(cartItem, "addOne");
        cartItemRepository.save(cartItem);
    }

    public void removeOneItemFromCart(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();

        int currentQuantity = cartItem.getQuantity();
        if (currentQuantity > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItemRepository.save(cartItem);
            publishCartEvent(cartItem, "removeOne");
        } else {
            cartItemRepository.delete(cartItem);
            publishCartEvent(cartItem, "remove");
        }
    }

    public BigDecimal calculateTotalCost() {
        List<CartItem> cartItems = getCartItems();
        return cartItems.stream()
                .map(item -> new BigDecimal(item.getProduct().getPrice().getValue())
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void publishCartEvent(CartItem cartItem, String operation) {
        publisher.publishEvent(new CartEvent(this, cartItem, operation));
    }
}
