package com.online.shop.services;


import com.online.shop.model.Cart;
import com.online.shop.model.CartItem;
import com.online.shop.model.Product;
import com.online.shop.model.User;
import com.online.shop.services.interfaces.CartItemService;
import com.online.shop.repositories.CartItemRepository;
import com.online.shop.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * Реалізація для сервісу елементів кошика.
 */
@Service
public class CartItemServiceImpl implements CartItemService {

    /**
     * Репозиторій елементів кошика.
     */
    private final CartItemRepository cartItemRepository;

    /**
     * Репозиторій користувачів.
     */
    private final UserRepository userRepository;

    /**
     * Повідомлення про відмову в доступі.
     */
    private final static String ACCESS_DENIED_MESSAGE = "access_denied";

    /**
     * Повідомлення про відсутність елемента кошика.
     */
    private final static String CART_ITEM_NOT_FOUND_MESSAGE = "cartitem_not_found";

    /**
     * Конструктор для внедрення залежностей.
     *
     * @param cartItemRepository репозиторій елементів кошика.
     * @param userRepository     репозиторій користувачів.
     */
    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    /**
     * Видаляє елемент кошика.
     *
     * @param id ідентифікатор елемента кошика.
     */
    @Override
    public void deleteCartItem(final Long id) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUserUsername = auth.getName();

            if (!cartItem.getCart().getUser().getUsername().equals(currentUserUsername)) {
                throw new AccessDeniedException(ACCESS_DENIED_MESSAGE);
            }

            Product product = cartItem.getProduct();
            product.deleteCartItem(cartItem);
        } else {
            throw new EntityNotFoundException(CART_ITEM_NOT_FOUND_MESSAGE);
        }

        cartItemRepository.deleteById(id);
    }

    /**
     * Змінює кількість елемента кошика.
     *
     * @param id       ідентифікатор елемента кошика.
     * @param quantity кількість елемента кошика.
     */
    @Override
    public void changeQuantity(final Long id, final Integer quantity) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUserUsername = auth.getName();

            if (!cartItem.getCart().getUser().getUsername().equals(currentUserUsername)) {
                throw new AccessDeniedException(ACCESS_DENIED_MESSAGE);
            }

            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        } else {
            throw new EntityNotFoundException(CART_ITEM_NOT_FOUND_MESSAGE);
        }
    }

    /**
     * Повертає елемент кошика.
     *
     * @param id ідентифікатор елемента кошика.
     * @return <code>CartItem</code>
     */
    @Override
    public CartItem getCartItem(final Long id) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);

        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUserUsername = auth.getName();

            if (!cartItem.getCart().getUser().getUsername().equals(currentUserUsername)) {
                throw new AccessDeniedException(ACCESS_DENIED_MESSAGE);
            }

            return cartItem;
        } else {
            throw new EntityNotFoundException(CART_ITEM_NOT_FOUND_MESSAGE);
        }
    }

    /**
     * Повертає всі елементи кошика.
     *
     * @param username ім'я користувача.
     * @return <code>Set of CartItem</code>
     */
    @Override
    public Set<CartItem> getCartItems(final String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Cart cart = user.getCart();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUserUsername = auth.getName();

            if (!user.getUsername().equals(currentUserUsername)) {
                throw new AccessDeniedException(ACCESS_DENIED_MESSAGE);
            }

            return cart.getItems();
        } else {
            throw new EntityNotFoundException(CART_ITEM_NOT_FOUND_MESSAGE);
        }
    }

    /**
     * Зберігає елемент кошика.
     *
     * @param cartItem елемент кошика.
     */
    @Override
    public void save(final CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }
}
