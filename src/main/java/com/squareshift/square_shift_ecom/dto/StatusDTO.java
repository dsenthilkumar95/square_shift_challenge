package com.squareshift.square_shift_ecom.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class StatusDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public StatusDTO() {}
    public StatusDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
