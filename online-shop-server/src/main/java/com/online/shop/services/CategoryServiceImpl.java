package com.online.shop.services;


import com.online.shop.model.Category;
import com.online.shop.services.interfaces.CategoryService;
import com.online.shop.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
