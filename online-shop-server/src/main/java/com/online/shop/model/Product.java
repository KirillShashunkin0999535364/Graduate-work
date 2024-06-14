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


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "products")
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private Double price;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JsonManagedReference
    @OneToMany(orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();
    @Lob
    @Column(name = "photo", length = 100000)
    @Nullable
    private byte[] photo;
    public void linkCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }
    public void deleteCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
    }
}
