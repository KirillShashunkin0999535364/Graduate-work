package com.online.shop.services;

import com.online.shop.exceptions.CouldntUploadPhotoException;
import com.online.shop.model.Cart;
import com.online.shop.model.CartItem;
import com.online.shop.model.Product;
import com.online.shop.model.User;
import com.online.shop.model.dto.ProductDTO;
import com.online.shop.services.interfaces.ProductService;
import com.online.shop.services.interfaces.UserService;
import com.online.shop.repositories.CartItemRepository;
import com.online.shop.repositories.ProductRepository;
import com.online.shop.utils.RequestBodyToEntityConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Реалізація сервісу продуктів.
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * Репозиторій продуктів.
     */
    private final ProductRepository productRepository;

    /**
     * Сервіс користувачів.
     */
    private final UserService userService;

    /**
     * Репозиторій елементів кошика.
     */
    private final CartItemRepository cartItemRepository;

    /**
     * Конструктор для внедрення залежностей.
     *
     * @param productRepository  це репозиторій продуктів.
     * @param userService        це сервіс користувачів.
     * @param cartItemRepository це репозиторій елементів кошика.
     */
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Повертає продукт.
     *
     * @param id це ідентифікатор продукту.
     * @return <code>Optional of Product</code>.
     */
    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product couldn't be found!"));
    }

    /**
     * Повертає всі продукти.
     *
     * @return <code>List of Products</code>
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Зберігає продукт.
     *
     * @param product це об'єкт продукту.
     */
    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    /**
     * Видаляє продукт.
     *
     * @param id це ідентифікатор продукту.
     */
    @Override
    public void delete(Long id) {
        Product product = this.getProduct(id);
        productRepository.deleteById(product.getId());
    }

    /**
     * Повертає продукти продавця.
     *
     * @param username це ім'я продавця.
     * @return Список продуктів.
     */
    @Override
    public List<Product> getSellerProducts(String username) {
        User user = userService.getByUsername(username);
        Long userId = user.getId();
        return productRepository.getProductsBySellerId(userId);
    }

    /**
     * Додає продукт до кошика.
     *
     * @param id       це ідентифікатор продукту.
     * @param quantity це кількість.
     */
    @Override
    public void addProductToCart(final Long id, final Integer quantity) {
        Product product = this.getProduct(id);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        Cart cart = user.getCart();
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setName(product.getName());
        cartItem.setPrice(product.getPrice());
        cartItemRepository.save(cartItem);
        product.linkCartItem(cartItem);
        productRepository.save(product);
    }

    /**
     * Додає новий продукт.
     *
     * @param productDTO це DTO продукту.
     * @return ідентифікатор продукту.
     */
    @Override
    public Long addProduct(ProductDTO productDTO) {
        Product product = RequestBodyToEntityConverter.convertToProduct(productDTO);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        product.setUser(user);
        productRepository.save(product);
        return product.getId();
    }

    /**
     * Оновлює фото продукту.
     *
     * @param id    це ідентифікатор продукту.
     * @param photo це фото продукту.
     */
    @Override
    public void updatePhoto(Long id, MultipartFile photo) {
        Product product = this.getProduct(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getByUsername(username);
        if (!product.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("access_denied");
        }
        try {
            product.setPhoto(photo.getBytes());
        } catch (IOException e) {
            throw new CouldntUploadPhotoException("product_photo_not_uploaded");
        }
        productRepository.save(product);
    }

    /**
     * Оновлює продукт.
     *
     * @param id         це ідентифікатор продукту.
     * @param productDTO це DTO продукту.
     */
    @Override
    public void updateProduct(Long id, ProductDTO productDTO) {
        Product product = this.getProduct(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserUsername = auth.getName();
        String productSellerUsername = product.getUser().getUsername();
        if (!currentUserUsername.equals(productSellerUsername)) {
            throw new AccessDeniedException("access_denied");
        }
        product.setName(productDTO.getName());
        product.setCategories(productDTO.getCategories());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        productRepository.save(product);
    }
}
