package com.yashmerino.online.shop.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Cart item DTO.
 */
@Getter
@Setter
public class CartItemDTO {

    /**
     * Cart item's ID.
     */
    private Long id;

    /**
     * Product's id.
     */
    private Long productId;

    /**
     * Cart Item's name.
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
     * Cart Item's price.
     */
    @NotNull(message = "price_is_required")
    @DecimalMin(value = "0.01", message = "price_value_error")
    private Double price;

    /**
     * Cart's id.
     */
    private Long cartId;

    /**
     * Quantity.
     */
    @NotNull(message = "quantity_is_required")
    @Min(value = 1L, message = "quantity_value_error")
    private Integer quantity;
}
