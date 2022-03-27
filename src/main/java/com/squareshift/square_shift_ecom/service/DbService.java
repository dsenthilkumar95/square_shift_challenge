package com.squareshift.square_shift_ecom.service;

import com.squareshift.square_shift_ecom.exception.DbAccessException;
import com.squareshift.square_shift_ecom.exception.ResponseException;
import com.squareshift.square_shift_ecom.model.Cart;
import com.squareshift.square_shift_ecom.model.CartId;
import com.squareshift.square_shift_ecom.model.Price;
import com.squareshift.square_shift_ecom.model.PriceId;
import com.squareshift.square_shift_ecom.repository.CartRepository;
import com.squareshift.square_shift_ecom.repository.PriceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class DbService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PriceRepository priceRepository;

    public Optional<Cart> getCartByProductId(int productId) throws DbAccessException {
        CartId cartId = new CartId();
        cartId.setUserId(1);
        cartId.setProductId(productId);
        try {
            Optional<Cart> productInCart = cartRepository.findById(cartId);
            return productInCart;
        } catch (Exception e) {
            log.error("DB access exception " + e.getMessage());
            throw new DbAccessException(e.getMessage());
        }
    }

    public boolean saveProduct(Cart cart) throws DbAccessException {
        try {
            cartRepository.save(cart);
            return true;
        } catch (Exception e) {
            throw new DbAccessException(e.getMessage());
        }
    }

    public List<Cart> getAllProductsByUser(int userId) throws DbAccessException {
        try {
            List<Cart> products = cartRepository.getAllProductsByUser(1);
            if (products != null){
                return products;
            } else {
                return new ArrayList<Cart>();
            }
        } catch (Exception e) {
            log.error("DB access exception " + e.getMessage());
            throw new DbAccessException(e.getMessage());
        }
    }

    public Optional<Integer> deleteAllProductsByUser(int userId) throws ResponseException {
        try {
            int rows = cartRepository.deleteAllProductsByUser(userId);
            if(rows > 0) {
                return Optional.of(rows);
            }
            throw new ResponseException("Cart already empty !!!");
        } catch (Exception e) {
            log.error("DB access exception " + e.getMessage());
            throw new ResponseException(e.getMessage());
        }
    }

    public Optional<Integer> getDeliveryPrice(float weight, float distance) throws DbAccessException {
        PriceId priceId = new PriceId();
        priceId.setDistance(getDbDistance(distance));
        priceId.setWeight(getDbWeight(weight));
        try {
            Optional<Price> priceOptional = priceRepository.findById(priceId);
            if(priceOptional.isPresent()) {
                int deliveryPrice = priceOptional.get().getPrice();
                return Optional.of(deliveryPrice);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DbAccessException(e.getMessage());
        }

    }

    private int getDbDistance(float distance) {
        if (distance >= 0 && distance <= 5) {
            return 0;
        } else if (distance > 5 && distance <= 20) {
            return 5;
        } else if (distance > 20 && distance <= 50) {
            return 20;
        } else if (distance > 50 && distance < 500) {
            return 50;
        } else if (distance > 500 && distance <= 800) {
            return 500;
        } else if (distance > 800) {
            return 800;
        }
        return 0;
    }

    private int getDbWeight(float weight) {
        if (weight >= 0 && weight <= 2) {
            return 0;
        } else if (weight > 2 && weight <= 5) {
            return 2;
        } else if (weight > 5 && weight <= 20) {
            return 5;
        } else if (weight > 20) {
            return 20;
        }
        return 0;
    }

}
