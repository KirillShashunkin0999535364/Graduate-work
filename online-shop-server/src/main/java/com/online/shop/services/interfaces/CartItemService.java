package com.online.shop.services.interfaces;

import com.online.shop.model.CartItem;

import java.util.Set;

public interface CartItemService {
    void deleteCartItem(final Long id);
    void changeQuantity(final Long id, final Integer quantity);
    CartItem getCartItem(final Long id);
    Set<CartItem> getCartItems(final String username);
    void save(final CartItem cartItem);
}
