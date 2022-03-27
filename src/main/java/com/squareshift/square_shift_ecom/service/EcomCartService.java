package com.squareshift.square_shift_ecom.service;

import com.squareshift.square_shift_ecom.dto.*;
import com.squareshift.square_shift_ecom.exception.DbAccessException;
import com.squareshift.square_shift_ecom.exception.ResponseException;

public interface EcomCartService {
    public CartStatusDTO addItemToCart(ItemDTO itemDTO) throws DbAccessException, ResponseException;
    public CartItemsDTO getItemsFromCart() throws DbAccessException, ResponseException;
    public CartEmptyDTO emptyCart(CartEmptyAction cartEmptyAction) throws DbAccessException, ResponseException;
    public Checkout checkoutCart(int shipping_postal_code) throws DbAccessException, ResponseException;
}
