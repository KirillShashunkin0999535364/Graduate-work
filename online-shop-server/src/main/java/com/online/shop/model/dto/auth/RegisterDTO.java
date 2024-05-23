package com.online.shop.model.dto.auth;


import com.online.shop.utils.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Register DTO.
 */
@Data
public class RegisterDTO {

    /**
     * User's role.
     */
    @NotNull(message = "Roles are required.")
    private Role role;

    /**
     * User's email.
     */
    @Email(message = "email_is_invalid", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z A-Z]{2,7}$")
    @NotNull(message = "email_is_required")
    @NotBlank(message = "email_is_required")
    // NOSONAR: The wrapper is required. Different error messages.
    @Size.List({
            @Size(min = 4, message = "email_too_short"),
            @Size(max = 255, message = "email_too_long")
    })
    private String email;

    /**
     * User's username.
     */
    @NotNull(message = "username_is_required")
    @NotBlank(message = "username_is_required")
    // NOSONAR: The wrapper is required. Different error messages.
    @Size.List({
            @Size(min = 4, message = "username_too_short"),
            @Size(max = 40, message = "username_too_long")
    })
    private String username;

    /**
     * User's password.
     */
    @NotNull(message = "password_is_required")
    @NotBlank(message = "password_is_required")
    // NOSONAR: The wrapper is required. Different error messages.
    @Size.List({
            @Size(min = 4, message = "password_too_short"),
            @Size(max = 40, message = "password_too_long")
    })
    private String password;
}
