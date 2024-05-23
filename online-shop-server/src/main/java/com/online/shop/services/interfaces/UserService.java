package com.online.shop.services.interfaces;


import com.online.shop.model.User;
import com.online.shop.model.dto.auth.UserDTO;
import com.online.shop.model.dto.auth.UserInfoDTO;
import org.springframework.web.multipart.MultipartFile;
/**
 * Інтерфейс сервісу користувача.
 */
public interface UserService {

    /**
     * Повертає користувача за ідентифікатором.
     *
     * @param id це ідентифікатор користувача.
     * @return <code>User</code>
     */
    User getById(final Long id);

    /**
     * Повертає користувача за ім'ям користувача.
     *
     * @param username це ім'я користувача.
     * @return <code>User</code>
     */
    User getByUsername(final String username);

    /**
     * Отримати інформацію про користувача за ім'ям користувача.
     *
     * @param username це ім'я користувача.
     * @return <code>UserInfoDTO</code>
     */
    UserInfoDTO getUserInfo(String username);

    /**
     * Оновлює фото для користувача.
     *
     * @param username це ім'я користувача.
     * @param photo    це фото користувача.
     */
    void updatePhoto(String username, MultipartFile photo);

    /**
     * Оновлює інформацію про користувача.
     *
     * @param username    це ім'я користувача.
     * @param userDTO     це оновлена інформація про користувача.
     */
    void updateUser(String username, UserDTO userDTO);

}
