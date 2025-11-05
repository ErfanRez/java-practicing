package com.danacup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_carts")
public class ShoppingCartEntity extends BaseEntity {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shoppingCart")
    private List<ShoppingCartItemEntity> items;

    @ManyToOne
    @JoinColumn
    private UserEntity user;

}