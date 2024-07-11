package com.gosave.gosave.services;

import com.gosave.gosave.controller.BeanConfig;
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

import static com.gosave.gosave.services.AppUserServiceImpl.getInitializeTransactionRequestHttpEntity;

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
        return getInitializeTransactionRequestHttpEntity(foundUser, beanConfig);
    }
}
