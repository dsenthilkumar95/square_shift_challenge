package com.squareshift.square_shift_ecom.exception;

import com.google.gson.Gson;
import com.squareshift.square_shift_ecom.dto.StatusDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@Log4j2
public class ResponseException extends Exception{
    public ResponseException(String message) {
        super(message);
    }
}
