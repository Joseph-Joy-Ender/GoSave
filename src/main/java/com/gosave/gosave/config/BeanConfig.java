package com.gosave.gosave.config;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class BeanConfig {
    @Value("${paystack.base.url}")
    private String paystackBaseUrl;
    @Value("${paystack.api.key}")
    private String paystackApiKey;
<<<<<<< HEAD:src/main/java/com/gosave/gosave/controller/BeanConfig.java
    @Value("https://api.paystack.co/transaction")
     private String transactionhistoryBaseUrl;
=======

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

>>>>>>> a8ef42e6f5169a08cf763d3d4fe09d1c01861450:src/main/java/com/gosave/gosave/config/BeanConfig.java
}
