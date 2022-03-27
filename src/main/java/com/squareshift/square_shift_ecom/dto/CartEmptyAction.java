package com.squareshift.square_shift_ecom.dto;

import lombok.Data;

@Data
public class CartEmptyAction {
    private String action;
    public CartEmptyAction() {}
    public CartEmptyAction(String action) {
        this.action = action;
    }
}
