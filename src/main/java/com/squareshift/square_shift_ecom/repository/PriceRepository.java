package com.squareshift.square_shift_ecom.repository;

import com.squareshift.square_shift_ecom.model.Price;
import com.squareshift.square_shift_ecom.model.PriceId;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, PriceId> {
}
