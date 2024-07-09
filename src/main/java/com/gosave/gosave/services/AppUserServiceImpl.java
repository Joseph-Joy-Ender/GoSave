package com.gosave.gosave.services;


import com.gosave.gosave.controller.BeanConfig;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.data.repositories.UserRepository;
import com.gosave.gosave.dto.request.InitializeTransactionRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.PayStackTransactionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private final UserRepository userRepository;
    private final BeanConfig beanConfig;
    @Override
    public ApiResponse<?> transferFunds(Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        Optional<User> foundUser = userRepository.findById(userId);
        HttpEntity<InitializeTransactionRequest> request = buildPaymentRequest(foundUser);
        ResponseEntity<PayStackTransactionResponse> response =
                restTemplate.postForEntity(beanConfig.getPaystackBaseUrl(), request, PayStackTransactionResponse.class);
        return new ApiResponse<>(response.getBody());
    }

    private HttpEntity<InitializeTransactionRequest> buildPaymentRequest(Optional<User> foundUser) {
        InitializeTransactionRequest transactionRequest = new InitializeTransactionRequest();
        transactionRequest.setEmail(foundUser.get().getEmail());
        transactionRequest.setAmount(foundUser.get().getAmount());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+beanConfig.getPaystackApiKey());
        return new HttpEntity<>(transactionRequest, headers);
    }
}


