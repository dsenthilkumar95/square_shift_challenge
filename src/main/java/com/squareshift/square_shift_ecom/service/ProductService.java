package com.squareshift.square_shift_ecom.service;

import com.squareshift.square_shift_ecom.dto.Warehouse;
import com.squareshift.square_shift_ecom.exception.ResponseException;
import com.squareshift.square_shift_ecom.model.Product;
import com.squareshift.square_shift_ecom.model.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.squareshift.square_shift_ecom.constants.CartConstants.SUCCESS_VALUE;

@Service
public class ProductService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${product.url}")
    private String productEndpointURL;

    public void isProductAvailable(int productId) throws ResponseException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(getProductEndpointURL());
        urlBuilder.append("/product/{id}");
        Map<String, Integer> uriParametersMap = new HashMap<>();
        uriParametersMap.put("id", productId);
        try {
            ResponseEntity<ProductResponse> productResponseEntity = restTemplate.getForEntity(urlBuilder.toString(), ProductResponse.class, uriParametersMap);
            if (!(HttpStatus.OK.equals(productResponseEntity.getStatusCode()))) {
                throw new ResponseException("Invalid Product Id");
            }
        } catch (Exception e) {
            throw new ResponseException("Invalid Product Id");
        }

    }

    public Optional<Product> getProductFromService(int productId) throws ResponseException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(getProductEndpointURL());
        urlBuilder.append("/product/{id}");
        Map<String, Integer> uriParametersMap = new HashMap<>();
        uriParametersMap.put("id", productId);
        try {
            ResponseEntity<ProductResponse> productResponseEntity = restTemplate.getForEntity(urlBuilder.toString(), ProductResponse.class, uriParametersMap);
            if(HttpStatus.OK.equals(productResponseEntity.getStatusCode())){
                ProductResponse productResponse = productResponseEntity.getBody();
                if(SUCCESS_VALUE.equals(productResponse.getStatus())) {
                    return Optional.of(productResponse.getProduct());
                } else {
                    throw new ResponseException("Product in cart not currently available");
                }
            } else {
                throw new ResponseException("Product in cart not currently available");
            }
        } catch (Exception e) {
            throw new ResponseException("Product in cart not currently available");
        }
    }

    public Optional<Float> getShippingDistance(int shipping_postal_code) throws ResponseException {
        StringBuilder shippingUrlBuilder = new StringBuilder();
        shippingUrlBuilder.append(getProductEndpointURL());
        shippingUrlBuilder.append("/warehouse/distance?postal_code={postal_code}");
        Map<String, Integer> shippingUriParametersMap = new HashMap<>();
        shippingUriParametersMap.put("postal_code", shipping_postal_code);
        try {
            ResponseEntity<Warehouse> warehouseResponseEntity = restTemplate.getForEntity(shippingUrlBuilder.toString(), Warehouse.class, shippingUriParametersMap);
            if(HttpStatus.OK.equals(warehouseResponseEntity.getStatusCode())) {
                Warehouse warehouse = warehouseResponseEntity.getBody();
                if(SUCCESS_VALUE.equals(warehouse.getStatus())) {
                    return Optional.of(warehouse.getDistance_in_kilometers());
                } else {
                    throw new ResponseException(warehouse.getMessage());
                }
            } else {
                throw new ResponseException(warehouseResponseEntity.getBody().getMessage());
            }
        } catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }

    private String getProductEndpointURL() {
        return productEndpointURL;
    }
}
