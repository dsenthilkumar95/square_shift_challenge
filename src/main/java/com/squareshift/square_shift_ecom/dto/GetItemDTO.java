package com.squareshift.square_shift_ecom.dto;

import lombok.Data;

@Data
public class GetItemDTO extends ItemDTO {
    private String description;
    public GetItemDTO() {}
    public GetItemDTO(int product_id, int quantity, String description) {
        super(product_id,quantity);
        this.description = description;
    }
}