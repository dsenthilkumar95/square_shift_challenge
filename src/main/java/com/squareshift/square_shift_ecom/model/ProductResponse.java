package com.squareshift.square_shift_ecom.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("product")
    private Product product;
}
