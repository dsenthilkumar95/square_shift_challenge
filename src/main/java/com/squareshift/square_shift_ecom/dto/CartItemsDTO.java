package com.squareshift.square_shift_ecom.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class CartItemsDTO extends StatusDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GetItemDTO> getItemDTOList;
    public CartItemsDTO() {}
    public CartItemsDTO(String status, String message, List<GetItemDTO> getItemDTOList) {
        super(status, message);
        this.getItemDTOList = getItemDTOList;
    }
}
