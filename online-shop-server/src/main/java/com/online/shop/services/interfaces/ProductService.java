package com.online.shop.services.interfaces;

import com.online.shop.model.Product;
import com.online.shop.model.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Product getProduct(final Long id);

    List<Product> getAllProducts();

    void save(final Product product);

    void delete(final Long id);

    List<Product> getSellerProducts(String username);

    void addProductToCart(final Long id, final Integer quantity);

    Long addProduct(final ProductDTO productDTO);

    void updatePhoto(Long id, MultipartFile photo);

    void updateProduct(Long id, ProductDTO productDTO);
}
