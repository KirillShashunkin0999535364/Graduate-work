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


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("username_not_found"));
    }

    @Override
    public UserInfoDTO getUserInfo(String username) {
        User user = getByUsername(username);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setRoles(user.getRoles());
        userInfoDTO.setEmail(user.getEmail());
        userInfoDTO.setFirstName(user.getFirstName());
        userInfoDTO.setLastName(user.getLastName());
        userInfoDTO.setAddress(user.getAddress());
        userInfoDTO.setPhoneNumber(user.getPhoneNumber());
        return userInfoDTO;
    }


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

    @Override
    public void updateUser(String username, UserDTO userDTO) {
        User user = this.getByUsername(username);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserUsername = auth.getName();

        if (!user.getUsername().equals(currentUserUsername)) {
            throw new AccessDeniedException("access_denied");
        }

        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        userRepository.save(user);
    }

}
