package com.online.shop.controllers;

import com.online.shop.model.Product;
import com.online.shop.model.dto.ProductDTO;
import com.online.shop.model.dto.SuccessDTO;
import com.online.shop.model.dto.SuccessWithIdDTO;
import com.online.shop.services.AlgoliaService;
import com.online.shop.services.interfaces.ProductService;
import com.online.shop.swagger.SwaggerConfig;
import com.online.shop.swagger.SwaggerHttpStatus;
import com.online.shop.swagger.SwaggerMessages;
import com.online.shop.utils.ApplicationProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.online.shop.utils.RequestBodyToEntityConverter.convertToProductDTO;

@Tag(name = "2. Контролери товарів", description = "Ці точки входу використовуються для виконання дій з товарами.")
@SecurityRequirement(name = SwaggerConfig.SECURITY_SCHEME_NAME)
@RestController
@RequestMapping("/api/product")
@Validated
public class ProductController {
    private final ProductService productService;
    private final AlgoliaService algoliaService;
    private final ApplicationProperties applicationProperties;

    public ProductController(ProductService productService, AlgoliaService algoliaService, ApplicationProperties applicationProperties) {
        this.productService = productService;
        this.algoliaService = algoliaService;
        this.applicationProperties = applicationProperties;
    }

    @Operation(summary = "Додає новий товар.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.PRODUCT_SUCCESSFULLY_ADDED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PostMapping
    public ResponseEntity<SuccessWithIdDTO> addProduct(@Validated @RequestBody ProductDTO productDTO) {
        Long productId = productService.addProduct(productDTO);

        SuccessWithIdDTO successDTO = new SuccessWithIdDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("product_added_successfully");
        successDTO.setId(productId);

        if (applicationProperties.isAlgoliaUsed) {
            algoliaService.addProductToIndex(convertToProductDTO(productService.getProduct(productId)));
        }

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    @Operation(summary = "Додає товар до кошика.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.ITEM_SUCCESSFULLY_ADDED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping("/{id}/add")
    public ResponseEntity<SuccessDTO> addProductToCart(@PathVariable Long id, @RequestParam Integer quantity) {
        productService.addProductToCart(id, quantity);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("product_added_to_cart_successfully");

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    @Operation(summary = "Повертає товар.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.RETURN_PRODUCT,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        ProductDTO productDTO = convertToProductDTO(product);

        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

@Operation(summary = "Повертає всі товари.")
@ApiResponses(value = {
        @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.RETURN_PRODUCTS,
                content = @Content),
        @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                content = @Content),
        @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                content = @Content),
        @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                content = @Content),
        @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                content = @Content)})
@GetMapping
public ResponseEntity<List<ProductDTO>> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    List<ProductDTO> productsDTO = new ArrayList<>();

    for (Product product : products) {
        productsDTO.add(convertToProductDTO(product));
    }

    return new ResponseEntity<>(productsDTO, HttpStatus.OK);
}

    @Operation(summary = "Видаляє товар.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.PRODUCT_SUCCESSFULLY_DELETED,
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
    public ResponseEntity<SuccessDTO> deleteProduct(@PathVariable Long id) {
        productService.delete(id);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("product_deleted_successfully");

        if (applicationProperties.isAlgoliaUsed) {
            algoliaService.deleteProductFromIndex(id);
        }

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    @Operation(summary = "Повертає всі товари продавця за ім'ям користувача.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.RETURN_SELLER_PRODUCTS,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping("/seller/{username}")
    public ResponseEntity<List<ProductDTO>> getSellerProducts(@PathVariable String username) {
        List<Product> products = productService.getSellerProducts(username);
        List<ProductDTO> productsDTO = new ArrayList<>();

        for (Product product : products) {
            productsDTO.add(convertToProductDTO(product));
        }

        return new ResponseEntity<>(productsDTO, HttpStatus.OK);
    }

    @Operation(summary = "Оновлює фотографію товару.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.PRODUCT_PHOTO_IS_UPDATED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SuccessDTO.class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.PRODUCT_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PostMapping(path = "/{id}/photo", consumes = "multipart/form-data")
    public ResponseEntity<SuccessDTO> setProductPhoto(@PathVariable Long id, @Parameter(description = "Фотографія товару.", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart("photo") MultipartFile photo) {
        productService.updatePhoto(id, photo);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("product_photo_updated_successfully");

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }

    @Operation(summary = "Повертає фотографію товару.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.PRODUCT_PHOTO_RETURNED,
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = byte[].class))}),
            @ApiResponse(responseCode = SwaggerHttpStatus.NOT_FOUND, description = SwaggerMessages.PRODUCT_DOES_NOT_EXIST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @GetMapping(path = "/{id}/photo")
    public ResponseEntity<byte[]> getProductPhoto(@PathVariable Long id) {
        byte[] photo = productService.getProduct(id).getPhoto();

        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    @Operation(summary = "Оновлює товар.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerHttpStatus.OK, description = SwaggerMessages.PRODUCT_SUCCESSFULLY_UPDATED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.BAD_REQUEST, description = SwaggerMessages.BAD_REQUEST,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.FORBIDDEN, description = SwaggerMessages.FORBIDDEN,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.UNAUTHORIZED, description = SwaggerMessages.UNAUTHORIZED,
                    content = @Content),
            @ApiResponse(responseCode = SwaggerHttpStatus.INTERNAL_SERVER_ERROR, description = SwaggerMessages.INTERNAL_SERVER_ERROR,
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<SuccessDTO> updateProduct(@PathVariable Long id, @Validated @RequestBody ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);

        SuccessDTO successDTO = new SuccessDTO();
        successDTO.setStatus(200);
        successDTO.setMessage("product_updated_successfully");

        if (applicationProperties.isAlgoliaUsed) {
            algoliaService.updateProduct(convertToProductDTO(productService.getProduct(id)));
        }

        return new ResponseEntity<>(successDTO, HttpStatus.OK);
    }
}
