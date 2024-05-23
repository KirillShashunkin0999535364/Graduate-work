package com.online.shop.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.online.shop.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA Entity for cart's item.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "cart_items")
@Table(name = "cart_items")
public class CartItem extends BaseEntity {

    /**
     * The cart item's product.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Cart Item's name.
     */
    private String name;

    /**
     * Cart Item's price.
     */
    private Double price;

    /**
     * The cart item's cart.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    /**
     * Quantity of cart's item.
     */
    private Integer quantity;
}
