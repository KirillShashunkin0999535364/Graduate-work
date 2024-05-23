package com.online.shop.services.interfaces;

import com.online.shop.model.CartItem;

import java.util.Set;
/**
 * Інтерфейс для сервісу елементів кошика.
 */
public interface CartItemService {

    /**
     * Видаляє елемент кошика.
     *
     * @param id це ідентифікатор елемента кошика.
     */
    void deleteCartItem(final Long id);

    /**
     * Змінює кількість елемента кошика.
     *
     * @param id       це ідентифікатор елемента кошика.
     * @param quantity це кількість елемента кошика.
     */
    void changeQuantity(final Long id, final Integer quantity);

    /**
     * Повертає елемент кошика.
     *
     * @param id це ідентифікатор елемента кошика.
     * @return <code>CartItem</code>
     */
    CartItem getCartItem(final Long id);

    /**
     * Повертає всі елементи кошика.
     *
     * @param username це ім'я користувача.
     * @return <code>List of CartItem</code>
     */
    Set<CartItem> getCartItems(final String username);

    /**
     * Зберігає елемент кошика.
     *
     * @param cartItem це елемент кошика.
     */
    void save(final CartItem cartItem);
}
