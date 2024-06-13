package com.online.shop.model.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public class NamedEntity extends BaseEntity{

    private String firstName;
    private String lastName;
}
