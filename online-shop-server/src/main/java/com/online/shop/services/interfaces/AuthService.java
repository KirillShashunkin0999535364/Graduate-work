package com.online.shop.services.interfaces;

import com.online.shop.model.dto.auth.LoginDTO;
import com.online.shop.model.dto.auth.RegisterDTO;

/**
 * Інтерфейс для сервісу аутентифікації/авторизації.
 */
public interface AuthService {

    /**
     * Реєструє користувача.
     *
     * @param registerDTO це об'єкт DTO реєстрації.
     */
    void register(final RegisterDTO registerDTO);

    /**
     * Виконує вхід користувача.
     *
     * @param loginDTO це об'єкт DTO входу.
     * @return JWT токен.
     */
    String login(final LoginDTO loginDTO);
}
