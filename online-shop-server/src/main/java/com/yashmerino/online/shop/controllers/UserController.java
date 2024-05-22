package com.yashmerino.online.shop.controllers;

import com.yashmerino.online.shop.model.dto.SuccessDTO;
import com.yashmerino.online.shop.model.dto.auth.UserDTO;
import com.yashmerino.online.shop.model.dto.auth.UserInfoDTO;
import com.yashmerino.online.shop.services.interfaces.AuthService;
import com.yashmerino.online.shop.services.interfaces.UserService;
import com.yashmerino.online.shop.swagger.SwaggerConfig;
import com.yashmerino.online.shop.swagger.SwaggerHttpStatus;
import com.yashmerino.online.shop.swagger.SwaggerMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
/**
 * Контролер користувачів.
 */
@Tag(name = "1. Користувач", description = "Ці точки доступу використовуються для маніпулювання користувачами.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    /**
     * Сервіс користувачів.
     */
    private final UserService userService;

    /**
     * Конструктор.
     *
     * @param userService це сервіс користувачів.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Отримати інформацію про користувача за ім'ям користувача.
     *
     * @param username це ім'я користувача.
     * @return <code>ResponseEntity</code>
     */
    @Operation(summary = "Повертає інформацію про користувача.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.USER_INFO_IS_RETURNED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserInfoDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.USER_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping("/{username}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@Parameter(description = "Ім'я користувача.") @PathVariable String username) {
        UserInfoDTO userInfoDTO = userService.getUserInfo(username);

        return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
    }

    /**
     * Встановити фото користувача.
     *
     * @param username це ім'я користувача.
     * @param photo це фото користувача.
     * @return <code>ResponseEntity</code>
     */
    @Operation(summary = "Оновлює фото користувача.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.USER_PHOTO_IS_UPDATED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.USER_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PostMapping(path = "/{username}/photo", consumes = "multipart/form-data")
    public ResponseEntity<SuccessDTO> setUserPhoto(@PathVariable String username, @Parameter(description = "Фото користувача.", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart("photo") MultipartFile photo) {
        userService.updatePhoto(username, photo);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("user_photo_updated_successfully");

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    /**
     * Повертає фото користувача у вигляді масиву байтів.
     *
     * @param username це ім'я користувача.
     * @return <code>ResponseEntity</code>
     */
    @Operation(summary = "Повертає фото користувача.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.USER_PHOTO_RETURNED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = byte[].class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.USER_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping(path = "/{username}/photo")
    public ResponseEntity<byte[]> getUserPhoto(@PathVariable String username) {
        byte[] photo = userService.getByUsername(username).getPhoto();

        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    /**
     * Оновлює інформацію про користувача.
     *
     * @param username    це ім'я користувача.
     * @param userDTO     це оновлена інформація про користувача.
     * @param phoneNumber це оновлений номер телефону користувача.
     * @return <code>ResponseEntity</code>
     */
    @Operation(summary = "Оновлює інформацію про користувача.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.USER_INFO_UPDATED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.USER_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PutMapping(path = "/{username}")
    public ResponseEntity<SuccessDTO> updateUser(@PathVariable String username,
                                                 @Parameter(description = "Оновлена інформація про користувача.")
                                                 @Validated @RequestBody UserDTO userDTO,
                                                 @Parameter(description = "Оновлений номер телефону користувача.")
                                                 @RequestParam String phoneNumber) {
        userService.updateUser(username, userDTO, phoneNumber);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("user_information_updated_successfully");

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }
}
