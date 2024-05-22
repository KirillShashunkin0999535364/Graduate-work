package com.yashmerino.online.shop.repositories;


import com.yashmerino.online.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cart Items' repository.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
