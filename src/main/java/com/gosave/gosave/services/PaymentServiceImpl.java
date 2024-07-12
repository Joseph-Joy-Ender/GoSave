package com.gosave.gosave.services;

import com.gosave.gosave.config.BeanConfig;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.data.repositories.UserRepository;
import com.gosave.gosave.dto.request.InitializeTransactionRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.PayStackTransactionResponse;
import com.gosave.gosave.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BeanConfig beanConfig;

    @Override
    public ApiResponse<?> transferFundsToWallet(Long userId) throws UserNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        Optional<User> foundUser = userRepository.findById(userId);

        if(foundUser.isPresent()) {
            HttpEntity<InitializeTransactionRequest> request = buildPaymentRequest(foundUser);
            ResponseEntity<PayStackTransactionResponse> response =
                    restTemplate.postForEntity(beanConfig.getPaystackBaseUrl(), request, PayStackTransactionResponse.class);
            System.out.println(response.getBody());
            return new ApiResponse<>(response.getBody());
        }else{
            throw new UserNotFoundException("user is not present");
        }
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
