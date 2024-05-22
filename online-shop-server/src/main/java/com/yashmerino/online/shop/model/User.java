package com.yashmerino.online.shop.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yashmerino.online.shop.model.base.NamedEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
/**
 * Сутність JPA для користувача.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
public class User extends NamedEntity {
    /**
     * Кошик користувача.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;
    /**
     * Продукти користувача.
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private Set<Product> products;
    /**
     * Email користувача.
     */
    private String email;
    /**
     * Ім'я користувача.
     */
    private String username;
    /**
     * Пароль користувача.
     */
    private String password;
    /**
     * Ролі користувача.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    /**
     * Фото користувача.
     */
    @Lob
    @Column(name = "photo", length = 100000)
    @Nullable
    private byte[] photo;

    /**
     * Номер телефону користувача.
     */
    private String phoneNumber;
}
