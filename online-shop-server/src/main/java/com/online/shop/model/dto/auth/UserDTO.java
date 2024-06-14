package com.online.shop.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @Email(message = "email_is_invalid", regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z A-Z]{2,7}$")
    @NotNull(message = "email_is_required")
    @NotBlank(message = "email_is_required")
    @Size.List({
            @Size(min = 4, message = "email_too_short"),
            @Size(max = 255, message = "email_too_long")
    })
    private String email;

    @NotNull(message = "first_name_is_required")
    @NotBlank(message = "first_name_is_required")
    @Size.List({
            @Size(min = 2, message = "first_name_too_short"),
            @Size(max = 255, message = "first_name_too_long")
    })
    private String firstName;

    @NotNull(message = "last_name_is_required")
    @NotBlank(message = "last_name_is_required")
    @Size.List({
            @Size(min = 2, message = "last_name_too_short"),
            @Size(max = 255, message = "last_name_too_long")
    })
    private String lastName;

    @NotNull(message = "address_is_required")
    @NotBlank(message = "address_is_required")
    @Size.List({
            @Size(min = 2, message = "address_too_short"),
            @Size(max = 255, message = "address_too_long")
    })
    private String address;

    @NotNull(message = "phone_number_is_required")
    @NotBlank(message = "phone_number_is_required")
    @Size.List({
            @Size(min = 10, message = "phone_number_too_short"),
            @Size(max = 20, message = "phone_number_too_long")
    })
    private String phoneNumber;
}
