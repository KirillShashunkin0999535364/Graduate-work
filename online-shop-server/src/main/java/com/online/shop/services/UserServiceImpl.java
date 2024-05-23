package com.online.shop.services;


import com.online.shop.exceptions.CouldntUploadPhotoException;
import com.online.shop.model.User;
import com.online.shop.services.interfaces.UserService;
import com.online.shop.model.dto.auth.UserDTO;
import com.online.shop.model.dto.auth.UserInfoDTO;
import com.online.shop.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Реалізація сервісу користувача.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Репозиторій користувачів.
     */
    private final UserRepository userRepository;

    /**
     * Конструктор для внедрення залежностей.
     *
     * @param userRepository це репозиторій користувачів.
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Повертає користувача за ідентифікатором.
     *
     * @param id це ідентифікатор користувача.
     * @return <code>User</code> або кидає виняток, якщо користувача не знайдено.
     */
    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
    }

    /**
     * Повертає користувача за ім'ям користувача.
     *
     * @param username це ім'я користувача.
     * @return <code>User</code> або кидає виняток, якщо користувача не знайдено.
     */
    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("username_not_found"));
    }

    /**
     * Отримати інформацію про користувача за ім'ям користувача.
     *
     * @param username це ім'я користувача.
     * @return <code>UserInfoDTO</code>
     */
    @Override
    public UserInfoDTO getUserInfo(String username) {
        User user = getByUsername(username);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setRoles(user.getRoles());
        userInfoDTO.setEmail(user.getEmail());
        return userInfoDTO;
    }

    /**
     * Оновлює фото для користувача.
     *
     * @param username це ім'я користувача.
     * @param photo    це фото користувача.
     */
    @Override
    public void updatePhoto(String username, MultipartFile photo) {
        User user = getByUsername(username);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserUsername = auth.getName();

        if (!user.getUsername().equals(currentUserUsername)) {
            throw new AccessDeniedException("access_denied");
        }

        try {
            user.setPhoto(photo.getBytes());
        } catch (IOException e) {
            throw new CouldntUploadPhotoException("user_photo_not_uploaded");
        }

        userRepository.save(user);
    }
    /**
     * Оновлює інформацію про користувача.
     *
     * @param username    це ім'я користувача.
     * @param userDTO     це оновлена інформація про користувача.
     */

    @Override
    public void updateUser(String username, UserDTO userDTO) {
        User user = this.getByUsername(username);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserUsername = auth.getName();

        if (!user.getUsername().equals(currentUserUsername)) {
            throw new AccessDeniedException("access_denied");
        }

        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
    }





}
