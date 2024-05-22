package com.yashmerino.online.shop.services;

import com.yashmerino.online.shop.exceptions.UserDoesntExistException;
import com.yashmerino.online.shop.exceptions.UsernameAlreadyTakenException;
import com.yashmerino.online.shop.model.Cart;
import com.yashmerino.online.shop.model.Role;
import com.yashmerino.online.shop.model.User;
import com.yashmerino.online.shop.model.dto.auth.LoginDTO;
import com.yashmerino.online.shop.model.dto.auth.RegisterDTO;
import com.yashmerino.online.shop.model.dto.auth.UserInfoDTO;
import com.yashmerino.online.shop.repositories.RoleRepository;
import com.yashmerino.online.shop.repositories.UserRepository;
import com.yashmerino.online.shop.security.JwtProvider;
import com.yashmerino.online.shop.services.interfaces.AuthService;
import com.yashmerino.online.shop.services.interfaces.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
/**
 * Реалізація для {@link AuthService}
 */
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * Менеджер аутентифікації.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Репозиторій користувачів.
     */
    private final UserRepository userRepository;

    /**
     * Репозиторій ролей.
     */
    private final RoleRepository roleRepository;

    /**
     * Кодувальник паролів.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Постачальник JWT токенів.
     */
    private final JwtProvider jwtProvider;

    /**
     * Сервіс кошика.
     */
    private final CartService cartService;

    /**
     * Конструктор.
     *
     * @param authenticationManager менеджер аутентифікації.
     * @param userRepository        репозиторій користувачів.
     * @param roleRepository        репозиторій ролей.
     * @param passwordEncoder       кодувальник паролів.
     * @param jwtProvider           постачальник JWT токенів.
     * @param cartService           сервіс кошика.
     */
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CartService cartService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.cartService = cartService;
    }

    /**
     * Реєструє користувача.
     */
    @Override
    public void register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) { // NOSONAR - Репозиторій користувачів не може бути null.
            throw new UsernameAlreadyTakenException("username_taken");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Optional<Role> roleOptional = roleRepository.findByName(registerDTO.getRole().name());


        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            user.setRoles(new HashSet<>(List.of(role)));
        } else {
            throw new EntityNotFoundException("role_not_found");
        }

        Cart cart = new Cart();
        cartService.save(cart);
        userRepository.save(user);

        user.setCart(cart);
        userRepository.save(user);

        cart.setUser(user);
        cartService.save(cart);
    }

    /**
     * Автентифікує користувача.
     *
     * @param loginDTO DTO для входу.
     * @return JWT токен.
     */
    @Override
    public String login(LoginDTO loginDTO) {
        if (!userRepository.existsByUsername(loginDTO.getUsername())) {
            throw new UserDoesntExistException("username_not_found");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }
}
