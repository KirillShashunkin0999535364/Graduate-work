package com.online.shop.model.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * Category's DTO.
 */
@Getter
@Setter
public class CategoryDTO {

    /**
     * Category's id.
     */
    private Long id;

    /**
     * Category's name.
     */
    private String name;
}
