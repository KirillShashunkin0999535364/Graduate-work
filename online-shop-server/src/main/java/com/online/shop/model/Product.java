package com.online.shop.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.online.shop.model.base.BaseEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * JPA Entity for a product.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "products")
@Table(name = "products")
public class Product extends BaseEntity {

    /**
     * Product's name.
     */
    private String name;

    /**
     * Product's price.
     */
    private Double price;

    /**
     * Product's description;
     */
    private String description;

    /**
     * Product's categories.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;

    /**
     * Product's seller.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The cart items that belong to this product.
     */
    @JsonManagedReference
    @OneToMany(orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    /**
     * Product's photo.
     */
    @Lob
    @Column(name = "photo", length = 100000)
    @Nullable
    private byte[] photo;

    /**
     * Links a cart item to the product.
     *
     * @param cartItem is the cart's item to link.
     */
    public void linkCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    /**
     * Deletes a cart item from linked cart items.
     *
     * @param cartItem is the cart's item.
     */
    public void deleteCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
    }
}
