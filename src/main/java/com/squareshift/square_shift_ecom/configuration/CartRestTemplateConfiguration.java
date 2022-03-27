package com.squareshift.square_shift_ecom.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CartRestTemplateConfiguration {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
        return restTemplate;
    }
}
