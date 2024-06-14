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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "carts")
@Table(name = "carts")
@JsonIgnoreProperties(value = { "intValue" })
public class Cart extends BaseEntity {
    @JsonManagedReference
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> items = new HashSet<>();
    @JsonIgnore
    @OneToOne(mappedBy = "cart")
    private User user;
    public void addItem(final CartItem item) {
        this.items.add(item);
    }
    public void addItems(final CartItem... items) {
        for (var item : items) {
            this.addItem(item);
        }
    }
    public void removeItem(final Long itemId) {
        this.items.removeIf(item -> item.getId().equals(itemId));
    }
    public void removeItems(final Long... itemsIds) {
        for (var itemId : itemsIds) {
            this.removeItem(itemId);
        }
    }
}
