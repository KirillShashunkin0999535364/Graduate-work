package com.yashmerino.online.shop.services;


import com.yashmerino.online.shop.model.Category;
import com.yashmerino.online.shop.repositories.CategoryRepository;
import com.yashmerino.online.shop.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реалізація сервісу категорій.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    /**
     * Репозиторій категорій.
     */
    private final CategoryRepository categoryRepository;

    /**
     * Конструктор для внедрення залежностей.
     *
     * @param categoryRepository це репозиторій категорій.
     */
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Повертає список категорій.
     *
     * @return список категорій.
     */
    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
