package com.yashmerino.online.shop.services.interfaces;


import com.yashmerino.online.shop.model.Category;

import java.util.List;

/**
 * Інтерфейс сервісу категорій.
 */
public interface CategoryService {

    /**
     * Повертає список категорій.
     *
     * @return список категорій.
     */
    List<Category> getCategories();
}
