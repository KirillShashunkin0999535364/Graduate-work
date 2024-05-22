package com.yashmerino.online.shop.controllers;

import com.yashmerino.online.shop.model.CartItem;
import com.yashmerino.online.shop.model.dto.CartItemDTO;
import com.yashmerino.online.shop.model.dto.SuccessDTO;
import com.yashmerino.online.shop.services.interfaces.CartItemService;
import com.yashmerino.online.shop.swagger.SwaggerConfig;
import com.yashmerino.online.shop.swagger.SwaggerHttpStatus;
import com.yashmerino.online.shop.swagger.SwaggerMessages;
import com.yashmerino.online.shop.utils.RequestBodyToEntityConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
/**
 * Контролер для елементів кошика.
 */
@Tag(name = "3. Контролер елементів кошика", description = "Ці точки входу використовуються для виконання дій з елементами кошика.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@RequestMapping("/api/cartItem")
@Validated
public class CartItemController {

    /**
     * Сервіс елементів кошика.
     */
    private final CartItemService cartItemService;

    /**
     * Конструктор.
     *
     * @param cartItemService сервіс елементів кошика.
     */
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }


    /**
     * Додає елемент кошика.
     *
     * @param id ідентифікатор елемента кошика.
     * @return <code>ResponseEntity</code>
     */
    @Operation(summary = "Видаляє елемент кошика.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.ITEM_SUCCESSFULLY_DELETED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessDTO> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("cartitem_deleted_successfully");

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    /**
     * Змінює кількість елемента кошика.
     *
     * @param id ідентифікатор елемента кошика.
     * @return <code>ResponseEntity</code>
     */
    @Operation(summary = "Змінює кількість елемента у кошику.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.QUANTITY_SUCCESSFULLY_CHANGED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PostMapping("/{id}/quantity")
    public ResponseEntity<SuccessDTO> changeQuantity(@PathVariable Long id, @Min(value = 1, message = "Кількість повинна бути більше або дорівнювати 1.") @RequestParam Integer quantity) {
        cartItemService.changeQuantity(id, quantity);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("quantity_changed_successfully");

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    /**
     * Повертає елемент кошика.
     *
     * @param id ідентифікатор елемента кошика.
     * @return <code>ResponseEntity</code>
     * @throws EntityNotFoundException якщо елемент кошика не може бути знайдений.
     */
    @Operation(summary = "Повертає елемент з кошика.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.RETURNED_CART_ITEM,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CartItemDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItem(@PathVariable Long id) {
        CartItem cartItem = cartItemService.getCartItem(id);
        CartItemDTO cartItemDTO = RequestBodyToEntityConverter.convertToCartItemDTO(cartItem);

        return new ResponseEntity<>(cartItemDTO, HttpStatus.OK);
    }

    /**
     * Повертає всі елементи кошика.
     *
     * @param username ім'я користувача.
     * @return <code>Список елементів кошика</code>
     */
    @Operation(summary = "Повертає всі елементи з кошика.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.RETURNED_CART_ITEM,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode
                    = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping
    public List<CartItemDTO> getCartItems(@RequestParam String username) {
        Set<CartItem> cartItemsSet = cartItemService.getCartItems(username);
        List<CartItemDTO> cartItems = new ArrayList<>();

        for (CartItem cartItem : cartItemsSet) {
            cartItems.add(RequestBodyToEntityConverter.convertToCartItemDTO(cartItem));
        }

        return cartItems;
    }
}
