package com.online.shop.controllers;

import com.online.shop.model.dto.SuccessDTO;
import com.online.shop.model.dto.auth.AuthResponseDTO;
import com.online.shop.model.dto.auth.LoginDTO;
import com.online.shop.model.dto.auth.RegisterDTO;
import com.online.shop.services.interfaces.AuthService;
import com.online.shop.swagger.SwaggerConfig;
import com.online.shop.swagger.SwaggerHttpStatus;
import com.online.shop.swagger.SwaggerMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Контролер для аутентифікації.
 */
@Tag(name = "1. Аутентифікація/Авторизація", description = "Ці точки входу використовуються для реєстрації/входу.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    /**
     * Сервіс аутентифікації/авторизації.
     */
    private AuthService authService;

    /**
     * Конструктор.
     *
     * @param authService сервіс аутентифікації/авторизації.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Реєструє нового користувача.
     *
     * @param registerDTO дані користувача.
     * @return <code>ResponseEntity</code>
     * @throws EntityNotFoundException якщо роль не знайдено.
     */
    @Operation(summary = "Реєструє нового користувача.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.USER_SUCCESSFULLY_REGISTERED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.USERNAME_IS_TAKEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PostMapping("/register")
    public ResponseEntity<SuccessDTO> register(@Parameter(description = "JSON Об'єкт з обліковими даними користувача.") @Valid @RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("user_registered_successfully");

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    /**
     * Вхід користувача.
     *
     * @param loginDTO дані користувача.
     * @return <code>ResponseEntity</code>
     */
    @Operation(summary = "Дозволяє користувачу увійти і отримати свій JWT-токен.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.USER_SIGNED_IN,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthResponseDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.USER_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Parameter(description = "JSON Об'єкт з обліковими даними користувача.") @Valid @RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }
}
