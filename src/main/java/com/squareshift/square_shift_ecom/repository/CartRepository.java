package com.squareshift.square_shift_ecom.repository;

import com.squareshift.square_shift_ecom.model.Cart;
import com.squareshift.square_shift_ecom.model.CartId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart, CartId> {
    @Query("select c from cart c where c.userId = ?1")
    public List<Cart> getAllProductsByUser(int userId);

    @Transactional
    @Modifying
    @Query("delete from cart c where c.userId = ?1")
    public int deleteAllProductsByUser(int userId);
}