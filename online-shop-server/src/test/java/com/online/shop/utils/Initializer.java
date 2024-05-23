package com.online.shop.utils;


import com.online.shop.model.*;

import com.online.shop.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Class that initializes data.
 */
@Component
@Profile("test")
public class Initializer implements CommandLineRunner {

    /**
     * Customers' repository.
     */
    private final UserRepository userRepository;

    /**
     * Carts' repository.
     */
    private final CartRepository cartRepository;

    /**
     * Cart items' repository.
     */
    private final CartItemRepository cartItemRepository;

    /**
     * Products' repository.
     */
    private final ProductRepository productRepository;

    /**
     * Categories' repository.
     */
    private final CategoryRepository categoryRepository;

    /**
     * Roles' repository.
     */
    private final RoleRepository roleRepository;

    /**
     * Constructor.
     *
     * @param userRepository     is the repository for customers.
     * @param cartRepository     is the repository for carts.
     * @param cartItemRepository is the repository for cart items.
     * @param productRepository  is the repository for products.
     * @param categoryRepository is the repository for categories.
     * @param roleRepository     is the repository  for roles.
     */
    public Initializer(UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, CategoryRepository categoryRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        com.online.shop.model.Role adminRole = new com.online.shop.model.Role();
        adminRole.setName("ADMIN");

        roleRepository.save(adminRole);

        com.online.shop.model.Role userRole = new com.online.shop.model.Role();
        userRole.setName(Role.USER.name());

        roleRepository.save(userRole);

        com.online.shop.model.Role sellerRole = new com.online.shop.model.Role();
        sellerRole.setName(Role.SELLER.name());

        roleRepository.save(sellerRole);

        byte[] photo = Files.readAllBytes(Path.of("src/test/resources/photos/photo.jpg"));

        User user = new User();
        user.setId(1L);
        user.setPhoto(photo);
        user.setUsername("user");
        user.setPassword("user");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);

        Cart cart = new Cart();
        cart.setId(1L);
        cartRepository.save(cart);

        user.setCart(cart);
        userRepository.save(user);

        cart.setUser(user);
        cartRepository.save(cart);

        User seller = new User();
        seller.setId(2L);
        seller.setUsername("seller");
        seller.setPassword("seller");
        seller.setRoles(new HashSet<>(Arrays.asList(sellerRole)));
        userRepository.save(seller);

        User anotherSeller = new User();
        anotherSeller.setId(3L);
        anotherSeller.setUsername("anotherSeller");
        anotherSeller.setPassword("anotherSeller");
        anotherSeller.setRoles(new HashSet<>(Arrays.asList(sellerRole)));
        userRepository.save(anotherSeller);

        Product product = new Product();
        product.setId(1L);
        product.setPhoto(photo);
        product.setUser(seller);
        product.setName("Phone");
        product.setPrice(5.0);

        productRepository.save(product);

        Product anotherProduct = new Product();
        anotherProduct.setId(2L);
        anotherProduct.setUser(anotherSeller);
        anotherProduct.setName("Laptop");
        anotherProduct.setPrice(3.0);

        productRepository.save(anotherProduct);

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setName(product.getName());
        cartItem.setPrice(product.getPrice());
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setCart(cart);
        cartItemRepository.save(cartItem);

        cart.setItems(new HashSet<>(Arrays.asList(cartItem)));
        cartRepository.save(cart);

        product.linkCartItem(cartItem);
        productRepository.save(product);

        Category digitalServices = new Category();
        digitalServices.setId(1L);
        digitalServices.setName("Digital Services");
        categoryRepository.save(digitalServices);

        Category cosmeticsAndBodyCare = new Category();
        cosmeticsAndBodyCare.setId(2L);
        cosmeticsAndBodyCare.setName("Cosmetics and Body Care");
        categoryRepository.save(cosmeticsAndBodyCare);

        Category foodAndBeverage = new Category();
        foodAndBeverage.setId(3L);
        foodAndBeverage.setName("Food and Beverage");
        categoryRepository.save(foodAndBeverage);

        Category furnitureAndDecor = new Category();
        furnitureAndDecor.setId(4L);
        furnitureAndDecor.setName("Furniture and Decor");
        categoryRepository.save(furnitureAndDecor);

        Category healthAndWellness = new Category();
        healthAndWellness.setId(5L);
        healthAndWellness.setName("Health and Wellness");
        categoryRepository.save(healthAndWellness);

        Category householdItems = new Category();
        householdItems.setId(6L);
        householdItems.setName("Household Items");
        categoryRepository.save(householdItems);

        Category media = new Category();
        media.setId(7L);
        media.setName("Media");
        categoryRepository.save(media);

        Category petCare = new Category();
        petCare.setId(8L);
        petCare.setName("Pet Care");
        categoryRepository.save(petCare);

        Category officeEquipment = new Category();
        officeEquipment.setId(9L);
        officeEquipment.setName("Office Equipment");
        categoryRepository.save(officeEquipment);
    }
}
