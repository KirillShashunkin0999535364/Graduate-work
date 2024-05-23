package com.online.shop.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.online.shop.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * JPA Entity for cart that holds products.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "carts")
@Table(name = "carts")
@JsonIgnoreProperties(value = { "intValue" })
public class Cart extends BaseEntity {

    /**
     * Cart's items.
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> items = new HashSet<>();

    /**
     * User of the cart.
     */
    @JsonIgnore
    @OneToOne(mappedBy = "cart")
    private User user;

    /**
     * Adds an item to the cart.
     *
     * @param item is the item to add.
     */
    public void addItem(final CartItem item) {
        this.items.add(item);
    }

    /**
     * Adds item to the cart.
     *
     * @param items is the list of items to add.
     */
    public void addItems(final CartItem... items) {
        for (var item : items) {
            this.addItem(item);
        }
    }

    /**
     * Removes an item from the cart.
     *
     * @param itemId is the item's id to remove.
     */
    public void removeItem(final Long itemId) {
        this.items.removeIf(item -> item.getId().equals(itemId));
    }

    /**
     * Removes items from the card.
     *
     * @param itemsIds is the list of items' ids to remove.
     */
    public void removeItems(final Long... itemsIds) {
        for (var itemId : itemsIds) {
            this.removeItem(itemId);
        }
    }
}
