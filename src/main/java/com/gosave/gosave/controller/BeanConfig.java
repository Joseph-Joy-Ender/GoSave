package com.gosave.gosave.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BeanConfig {
    @Value("${paystack.base.url}")
    private String paystackBaseUrl;
    @Value("${paystack.api.key}")
    private String paystackApiKey;

}
