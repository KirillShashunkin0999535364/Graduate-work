package com.yashmerino.online.shop.model.dto.auth;

import com.yashmerino.online.shop.model.Role;
import lombok.Data;

import java.util.Set;

/**
 * User Information DTO.
 */
@Data
public class UserInfoDTO {

    /**
     * User's roles.
     */
    private Set<Role> roles;

    /**
     * User's email.
     */
    private String email;
}

