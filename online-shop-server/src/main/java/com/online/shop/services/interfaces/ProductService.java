package com.online.shop.services.interfaces;

import com.online.shop.model.Product;
import com.online.shop.model.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * Інтерфейс сервісу продукту.
 */
public interface ProductService {

    /**
     * Повертає продукт.
     *
     * @param id це ідентифікатор продукту.
     * @return <code>Product</code>
     */
    Product getProduct(final Long id);

    /**
     * Повертає всі продукти.
     *
     * @return <code>List of Products</code>
     */
    List<Product> getAllProducts();

    /**
     * Зберігає продукт.
     *
     * @param product це об'єкт продукту.
     */
    void save(final Product product);

    /**
     * Видаляє продукт.
     *
     * @param id це ідентифікатор продукту.
     */
    void delete(final Long id);

    /**
     * Повертає продукти продавця.
     *
     * @param username це ім'я продавця.
     * @return List of Products.
     */
    List<Product> getSellerProducts(String username);

    /**
     * Додає продукт у кошик.
     *
     * @param id       це ідентифікатор продукту.
     * @param quantity це кількість.
     */
    void addProductToCart(final Long id, final Integer quantity);

    /**
     * Додає новий продукт.
     *
     * @param productDTO це DTO продукту.
     * @return ідентифікатор продукту.
     */
    Long addProduct(final ProductDTO productDTO);

    /**
     * Оновлює фото продукту.
     *
     * @param id    це ідентифікатор продукту.
     * @param photo це фото продукту.
     */
    void updatePhoto(Long id, MultipartFile photo);

    /**
     * Оновлює продукт.
     *
     * @param id         це ідентифікатор продукту.
     * @param productDTO це DTO продукту.
     */
    void updateProduct(Long id, ProductDTO productDTO);
}
