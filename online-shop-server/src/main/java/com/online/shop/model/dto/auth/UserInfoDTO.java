package com.online.shop.model.dto.auth;

import com.online.shop.model.Role;
import lombok.Data;

import java.util.Set;


@Data
public class UserInfoDTO {
    private Set<Role> roles;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
}

