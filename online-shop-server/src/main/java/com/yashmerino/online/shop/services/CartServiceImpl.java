package com.yashmerino.online.shop.services;

import com.yashmerino.online.shop.model.Cart;
import com.yashmerino.online.shop.repositories.CartRepository;
import com.yashmerino.online.shop.services.interfaces.CartService;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * Реалізація сервісу кошика.
 */
@Service
public class CartServiceImpl implements CartService {

    /**
     * Репозиторій кошика.
     */
    private final CartRepository cartRepository;

    /**
     * Конструктор для внедрення залежностей.
     *
     * @param cartRepository це репозиторій кошика.
     */
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Повертає кошик.
     *
     * @param id це ідентифікатор кошика.
     * @return <code>Optional of Cart</code>.
     */
    @Override
    public Optional<Cart> getCart(Long id) {
        return cartRepository.findById(id);
    }

    /**
     * Зберігає кошик.
     *
     * @param cart це кошик, який потрібно зберегти.
     */
    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
