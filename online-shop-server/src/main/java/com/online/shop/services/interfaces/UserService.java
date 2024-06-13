package com.online.shop.services.interfaces;


import com.online.shop.model.User;
import com.online.shop.model.dto.auth.UserDTO;
import com.online.shop.model.dto.auth.UserInfoDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User getById(final Long id);

    User getByUsername(final String username);
    UserInfoDTO getUserInfo(String username);
    void updatePhoto(String username, MultipartFile photo);
    void updateUser(String username, UserDTO userDTO);

}
