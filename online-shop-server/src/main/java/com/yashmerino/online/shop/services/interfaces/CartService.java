package com.yashmerino.online.shop.services.interfaces;


import com.yashmerino.online.shop.model.Cart;

import java.util.Optional;

/**
 * Інтерфейс сервісу кошика.
 */
public interface CartService {

    /**
     * Повертає кошик.
     *
     * @param id це ідентифікатор кошика.
     */
    Optional<Cart> getCart(final Long id);

    /**
     * Зберігає кошик.
     *
     * @param cart це кошик, який потрібно зберегти.
     */
    void save(final Cart cart);

}
