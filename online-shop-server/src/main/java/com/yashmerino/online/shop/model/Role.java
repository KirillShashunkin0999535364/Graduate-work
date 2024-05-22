package com.yashmerino.online.shop.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * User's roles.
 */
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    /**
     * Role's id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Role's name.
     */
    @NonNull
    private String name;
}
