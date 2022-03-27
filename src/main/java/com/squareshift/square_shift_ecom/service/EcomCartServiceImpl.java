package com.squareshift.square_shift_ecom.service;

import com.squareshift.square_shift_ecom.dto.*;
import com.squareshift.square_shift_ecom.exception.DbAccessException;
import com.squareshift.square_shift_ecom.exception.ResponseException;
import com.squareshift.square_shift_ecom.model.Cart;
import com.squareshift.square_shift_ecom.model.Product;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.squareshift.square_shift_ecom.constants.CartConstants.EMPTY_CART;

@Service
@Log4j2
public class EcomCartServiceImpl implements EcomCartService {

    @Autowired
    private ProductService productService;

    @Autowired
    private DbService dbService;

    @Override
    public CartStatusDTO addItemToCart(ItemDTO itemDTO) throws DbAccessException, ResponseException {
        CartStatusDTO cartStatusDTO = new CartStatusDTO();
        Optional<Cart> productInCart = dbService.getCartByProductId(itemDTO.getProduct_id());
        if (productInCart.isPresent()) {
            Cart cartProduct = productInCart.get();
            int quantity = cartProduct.getQuantity() + itemDTO.getQuantity();
            cartProduct.setQuantity(quantity);
            dbService.saveProduct(cartProduct);
        } else {
            productService.isProductAvailable(itemDTO.getProduct_id());
            Cart cart = new Cart();
            cart.setUserId(1);
            cart.setProductId(itemDTO.getProduct_id());
            cart.setQuantity(itemDTO.getQuantity());
            dbService.saveProduct(cart);
        }
        cartStatusDTO.setStatus("success");
        cartStatusDTO.setMessage("Item has been added to cart");
        return cartStatusDTO;
    }

    @Override
    public CartItemsDTO getItemsFromCart() throws DbAccessException, ResponseException {
        CartItemsDTO cartItemsDTO = new CartItemsDTO();
        List<Cart> allProductsByUser = dbService.getAllProductsByUser(1);
        if (allProductsByUser.size() > 0) {
            List<GetItemDTO> getItemDTOList = new ArrayList<>();
            for (Cart product : allProductsByUser) {
                GetItemDTO getItemDTO = new GetItemDTO();
                getItemDTO.setProduct_id(product.getProductId());
                getItemDTO.setQuantity(product.getQuantity());
                Optional<Product> optionalProduct = productService.getProductFromService(product.getProductId());
                String description = optionalProduct.isPresent() ? optionalProduct.get().getDescription() : null;
                getItemDTO.setDescription(description);
                getItemDTOList.add(getItemDTO);
            }
            cartItemsDTO.setStatus("success");
            cartItemsDTO.setMessage("Item available in the cart");
            cartItemsDTO.setGetItemDTOList(getItemDTOList);
        } else {
            throw new ResponseException("Cart is empty");
        }
        return cartItemsDTO;
    }

    @Override
        public CartEmptyDTO emptyCart(CartEmptyAction cartEmptyAction) throws ResponseException {
        CartEmptyDTO cartEmptyDTO = new CartEmptyDTO();
        if(EMPTY_CART.equals(cartEmptyAction.getAction())) {
            Optional<Integer> optionalRows = dbService.deleteAllProductsByUser(1);
            if(optionalRows.isPresent()) {
                cartEmptyDTO.setStatus("success");
                cartEmptyDTO.setMessage("All items have been removed from the cart !");
            }
        } else {
            throw new ResponseException("Action not available");
        }

        return cartEmptyDTO;
    }

    @Override
    public Checkout checkoutCart(int shipping_postal_code) throws DbAccessException, ResponseException {
        Checkout checkout = new Checkout();
        List<Cart> allProductsByUser = dbService.getAllProductsByUser(1);
        float totalPrice = 0;
        if (allProductsByUser.size() > 0) {
            float totalProductPrice = 0;
            float totalWeight = 0;
            for (Cart product : allProductsByUser) {
                Optional<Product> optionalProduct = productService.getProductFromService(product.getProductId());
                if (optionalProduct.isPresent()) {
                    Product product1 = optionalProduct.get();
                    float price = product1.getPrice() >= 0 ? product1.getPrice() : 0;
                    float discount = product1.getDiscount_percentage() >= 0 ? product1.getDiscount_percentage() : 0;
                    float productPrice = price * (1 - (discount / 100)) * product.getQuantity();
                    float weight = product1.getWeight_in_grams() >= 0 ? product1.getWeight_in_grams() : 0;
                    totalProductPrice += productPrice;
                    totalWeight += weight;
                }
            }
            totalPrice += totalProductPrice;
            float distance = 0;
            Optional<Float> shippingDistance = productService.getShippingDistance(shipping_postal_code);
            if(shippingDistance.isPresent()) {
                distance = shippingDistance.get();
            }
            Optional<Integer> deliveryPrice = dbService.getDeliveryPrice(totalWeight, distance);
            if(deliveryPrice.isPresent()){
                totalPrice += deliveryPrice.get();
            }
        } else {
            throw new ResponseException("Cart is empty");
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        float twoDeciTotalPrice = Float.valueOf(decimalFormat.format(totalPrice));
        checkout.setStatus("success");
        checkout.setMessage("Total value of your shopping cart is - $" + String.valueOf(twoDeciTotalPrice));
        return checkout;
    }
}
