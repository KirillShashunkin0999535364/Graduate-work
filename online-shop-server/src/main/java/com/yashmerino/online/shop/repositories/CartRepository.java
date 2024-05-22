package com.yashmerino.online.shop.repositories;

import com.yashmerino.online.shop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Carts' repository.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
