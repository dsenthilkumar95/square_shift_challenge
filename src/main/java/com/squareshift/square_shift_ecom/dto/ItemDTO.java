package com.squareshift.square_shift_ecom.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
public class ItemDTO {
    @NotEmpty
    private int product_id;
    @NotEmpty
    @Min(1)
    private int quantity;
    public ItemDTO() {}
    public ItemDTO(int product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }
}
