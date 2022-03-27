package com.squareshift.square_shift_ecom.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "cart")
@Table(name = "cart")
@IdClass(CartId.class)
public class Cart {
    @Id
    private int userId;
    @Id
    private int productId;
    @Column
    private int quantity;
}
