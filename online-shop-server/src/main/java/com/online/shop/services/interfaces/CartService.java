package com.online.shop.services.interfaces;


import com.online.shop.model.Cart;

import java.util.Optional;


public interface CartService {

    Optional<Cart> getCart(final Long id);
    void save(final Cart cart);

}
