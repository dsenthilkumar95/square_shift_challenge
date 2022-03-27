package com.squareshift.square_shift_ecom.configuration;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareshift.square_shift_ecom.exception.DbAccessException;
import com.squareshift.square_shift_ecom.exception.ResponseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EcomCartControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DbAccessException.class})
    public ResponseEntity<Object> handleDbAccessException(DbAccessException dbe, WebRequest request) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status","error");
        jsonObject.addProperty("message", "Error occurred in persisting the data");
        String bodyOfResponse = null;
        bodyOfResponse = new Gson().toJson(jsonObject);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");
        return new ResponseEntity(bodyOfResponse, httpHeaders, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(value = {ResponseException.class})
    public ResponseEntity<Object> handleResponseException(ResponseException re, WebRequest request) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status","error");
        jsonObject.addProperty("message", re.getMessage());
        String bodyOfResponse = null;
        bodyOfResponse = new Gson().toJson(jsonObject);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");
        return new ResponseEntity(bodyOfResponse, httpHeaders, HttpStatus.BAD_REQUEST);
    }
}
