package com.squareshift.square_shift_ecom.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
public class CartId implements Serializable {
    private int userId;
    private int productId;

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if(o==null || getClass() != o.getClass()) return false;
        CartId cartId = (CartId) o;
        return userId == cartId.userId && productId==cartId.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}
