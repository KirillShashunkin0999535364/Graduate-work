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


@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final static String ACCESS_DENIED_MESSAGE = "access_denied";

    private final static String CART_ITEM_NOT_FOUND_MESSAGE = "cartitem_not_found";

    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

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

    @Override
    public void save(final CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }
}
