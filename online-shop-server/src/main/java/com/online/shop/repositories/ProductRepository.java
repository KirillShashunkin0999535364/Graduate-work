package com.online.shop.repositories;
import com.online.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Products' repository.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Return seller's products.
     *
     * @param userId is the seller's id.
     * @return List of Products.
     */
    @Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.user_id = ?1")
    List<Product> getProductsBySellerId(Long userId);
}
