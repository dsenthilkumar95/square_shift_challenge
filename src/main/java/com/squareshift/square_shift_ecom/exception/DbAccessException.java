package com.squareshift.square_shift_ecom.exception;

public class DbAccessException extends Exception{
    public DbAccessException() {}
    public DbAccessException(String message) {
        super(message);
    }
}