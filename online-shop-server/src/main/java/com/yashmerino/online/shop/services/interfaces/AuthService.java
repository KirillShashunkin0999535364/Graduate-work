package com.yashmerino.online.shop.services.interfaces;

import com.yashmerino.online.shop.model.dto.auth.LoginDTO;
import com.yashmerino.online.shop.model.dto.auth.RegisterDTO;

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
