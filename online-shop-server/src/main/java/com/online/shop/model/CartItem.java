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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "cart_items")
@Table(name = "cart_items")
public class CartItem extends BaseEntity {
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String name;
    private Double price;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private Integer quantity;
}
