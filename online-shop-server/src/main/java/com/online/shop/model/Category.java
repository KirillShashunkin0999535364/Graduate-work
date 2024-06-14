package com.online.shop.model;

import com.online.shop.model.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "categories")
@Table(name = "categories")
public class Category extends BaseEntity {
    private String name;
}
