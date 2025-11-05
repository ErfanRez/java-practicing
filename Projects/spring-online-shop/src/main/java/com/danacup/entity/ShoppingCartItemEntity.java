package com.danacup.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart_items")
public class ShoppingCartItemEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn
    private ShoppingCartEntity shoppingCart;

    @ManyToOne
    @JoinColumn
    private ProductEntity product;

    @Column
    private Integer quantity;

}