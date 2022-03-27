package com.squareshift.square_shift_ecom.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
public class PriceId implements Serializable {
    private int weight;
    private int distance;

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || getClass() != o.getClass()) return false;
        PriceId priceId = (PriceId) o;
        return weight == priceId.weight && distance==priceId.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, distance);
    }
}
