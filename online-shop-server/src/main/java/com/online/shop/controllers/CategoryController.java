package com.online.shop.controllers;

import com.online.shop.model.Category;
import com.online.shop.model.dto.CategoryDTO;
import com.online.shop.services.interfaces.CategoryService;
import com.online.shop.swagger.SwaggerConfig;
import com.online.shop.swagger.SwaggerHttpStatus;
import com.online.shop.swagger.SwaggerMessages;
import com.online.shop.utils.RequestBodyToEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "5. Категорії", description = "Ці точки входу використовуються для управління категоріями.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Отримує всі категорії.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.CATEGORIES_SUCCESSFULLY_RETRIEVED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        List<CategoryDTO> categoriesDTO = new ArrayList<>();

        for (Category category : categories) {
            categoriesDTO.add(RequestBodyToEntityConverter.convertToCategoryDTO(category));
        }

        return new ResponseEntity<>(categoriesDTO, HttpStatus.OK);
    }
}
