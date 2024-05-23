package com.online.shop.repositories;


import com.online.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Cart Items' repository.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
