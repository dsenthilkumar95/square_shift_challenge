package com.squareshift.square_shift_ecom.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "price")
@Table(name = "price")
@IdClass(PriceId.class)
public class Price {
    @Id
    private int weight;
    @Id
    private int distance;
    @Column
    private int price;
}
