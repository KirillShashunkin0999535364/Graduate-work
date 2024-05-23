package com.online.shop.repositories;

import com.online.shop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Carts' repository.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
