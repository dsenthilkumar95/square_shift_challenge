package com.squareshift.square_shift_ecom.controller;

import com.squareshift.square_shift_ecom.dto.*;
import com.squareshift.square_shift_ecom.exception.DbAccessException;
import com.squareshift.square_shift_ecom.exception.ResponseException;
import com.squareshift.square_shift_ecom.service.EcomCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cart")
public class EcomCartController {
    @Autowired
    private EcomCartService ecomCartService;

    @PostMapping(path = "/item",produces = "application/json", consumes = "application/json")
    public CartStatusDTO addItemToCart(@RequestBody ItemDTO itemDTO, BindingResult errors) throws DbAccessException, ResponseException {
        if(itemDTO.getQuantity() <= 0) {
            throw new ResponseException("Quantity can\'t be zero or negative");
        }
        return ecomCartService.addItemToCart(itemDTO);
    }

    @GetMapping(path = "/items",produces = "application/json")
    public CartItemsDTO getItemsFromCart() throws DbAccessException, ResponseException {
        return ecomCartService.getItemsFromCart();
    }

    @PostMapping(path = "/items",produces = "application/json", consumes = "application/json")
    public CartEmptyDTO emptyCart(@RequestBody CartEmptyAction cartEmptyAction) throws DbAccessException, ResponseException {
        return ecomCartService.emptyCart(cartEmptyAction);
    }

    @GetMapping(path = "/checkout-value",produces = "application/json")
    public Checkout checkoutCart(@RequestParam int shipping_postal_code) throws DbAccessException, ResponseException {
        return ecomCartService.checkoutCart(shipping_postal_code);
    }
}
