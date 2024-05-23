package com.online.shop.utils;


import com.online.shop.model.CartItem;
import com.online.shop.model.Category;
import com.online.shop.model.Product;
import com.online.shop.model.dto.CartItemDTO;
import com.online.shop.model.dto.CategoryDTO;
import com.online.shop.model.dto.ProductDTO;

/**
 * Утилітарний клас, який перетворює тіло запиту в сутність.
 */
public class RequestBodyToEntityConverter {

    /**
     * Приватний конструктор для заборони інстанціювання.
     */
    private RequestBodyToEntityConverter() {

    }

    /**
     * Перетворює об'єкт productDTO в екземпляр сутності product.
     *
     * @param productDTO це об'єкт DTO продукту.
     * @return <code>Product</code>
     */
    public static Product convertToProduct(final ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategories(productDTO.getCategories());

        return product;
    }

    /**
     * Перетворює продукт в об'єкт ProductDTO.
     *
     * @param product це сутність продукту.
     * @return <code>ProductDTO</code>
     */
    public static ProductDTO convertToProductDTO(final Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId().toString());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategories(product.getCategories());

        return productDTO;
    }

    /**
     * Перетворює сутність елементу кошика в DTO елементу кошика.
     *
     * @param cartItem це сутність елементу кошика.
     * @return <code>CartItemDTO</code>
     */
    public static CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setCartId(cartItem.getCart().getId());
        cartItemDTO.setName(cartItem.getName());
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());

        return cartItemDTO;
    }

    /**
     * Перетворює сутність категорії в DTO категорії.
     *
     * @param category це сутність категорії.
     * @return <code>CategoryDTO</code>
     */
    public static CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());

        return categoryDTO;
    }
}
