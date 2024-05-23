package com.online.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.online.shop.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Product DTO.
 */
@Getter
@Setter
@JsonPropertyOrder({"objectID", "name", "price", "categories"})
public class ProductDTO {

    /**
     * Product's id.
     */
    @JsonProperty("objectID")
    private String id;

    /**
     * Product's name;
     */
    @NotNull(message = "name_is_required")
    @NotBlank(message = "name_is_required")
    // NOSONAR: The wrapper is required. Different error messages.
    @Size.List({
            @Size(min = 4, message = "name_too_short"),
            @Size(max = 40, message = "name_too_long")
    })
    private String name;

    /**
     * Product's description;
     */
    @Size(max = 100, message = "description_too_long")
    private String description;

    /**
     * Product's Price.
     */
    @NotNull(message = "price_is_required")
    @DecimalMin(value = "0.01", message = "price_value_error")
    private Double price;

    /**
     * Product's categories.
     */
    private Set<Category> categories;
}
