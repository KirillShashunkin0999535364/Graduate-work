package com.online.shop.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartItemDTO {
    private Long id;
    private Long productId;

    @NotNull(message = "name_is_required")
    @NotBlank(message = "name_is_required")
    @Size.List({
            @Size(min = 4, message = "name_too_short"),
            @Size(max = 40, message = "name_too_long")
    })
    private String name;

    @NotNull(message = "price_is_required")
    @DecimalMin(value = "0.01", message = "price_value_error")
    private Double price;
    private Long cartId;
    @NotNull(message = "quantity_is_required")
    @Min(value = 1L, message = "quantity_value_error")
    private Integer quantity;
}
