package com.online.shop.services.interfaces;

import com.online.shop.model.dto.auth.LoginDTO;
import com.online.shop.model.dto.auth.RegisterDTO;


public interface AuthService {
    void register(final RegisterDTO registerDTO);
    String login(final LoginDTO loginDTO);
}
