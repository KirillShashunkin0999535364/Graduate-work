package com.online.shop.repositories;

import com.online.shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Categories' repository.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}