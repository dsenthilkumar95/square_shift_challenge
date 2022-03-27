package com.squareshift.square_shift_ecom.configuration;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateErrorHandler
        implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (httpResponse.getStatusCode() != HttpStatus.OK && httpResponse.getRawStatusCode() < 400 && httpResponse.getRawStatusCode()>=500);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {
        if(httpResponse.getRawStatusCode() >=500){
            byte[] bytes = IOUtils.toByteArray(httpResponse.getBody());
            throw HttpServerErrorException.create(httpResponse.getStatusCode(), httpResponse.getStatusText(), httpResponse.getHeaders(), bytes, null);
        }else {
            throw new IOException(httpResponse.getStatusText());
        }
    }
}
