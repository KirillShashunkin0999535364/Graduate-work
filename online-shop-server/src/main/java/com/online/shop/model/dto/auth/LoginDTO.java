package com.online.shop.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LoginDTO {

    @NotNull(message = "username_is_required")
    @NotBlank(message = "username_is_required")
    private String username;

    @NotNull(message = "password_is_required")
    @NotBlank(message = "password_is_required")
    private String password;
}
