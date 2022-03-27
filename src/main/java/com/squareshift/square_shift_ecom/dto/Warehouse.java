package com.squareshift.square_shift_ecom.dto;

import lombok.Data;

@Data
public class Warehouse extends StatusDTO{
    private float distance_in_kilometers;
    public Warehouse() {}
    public Warehouse(String status, String message, float distance_in_kilometers) {
        super(status,message);
        this.distance_in_kilometers = distance_in_kilometers;
    }
}