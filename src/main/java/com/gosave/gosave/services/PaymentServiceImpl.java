
package com.gosave.gosave.services;

import com.gosave.gosave.config.BeanConfig;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.UserRepository;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.InitializeTransactionRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.PayStackTransactionResponse;
import com.gosave.gosave.dto.response.TransactionHistoryResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserNotFoundException;
import com.gosave.gosave.exception.WalletNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.modelmapper.ModelMapper;
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
    private final UserRepository userRepository;
    private final BeanConfig beanConfig;
    @Autowired
    private final ModelMapper mapper = new ModelMapper();
    @Autowired
    private final WalletRepository walletRepository;




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
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+ beanConfig.getPaystackApiKey());
        return new HttpEntity<>(transactionRequest, headers);
    }
    @Override
    public WalletResponse getBalance(Long walletId) throws WalletNotFoundException {
        return mapper.map(findWalletBy(walletId), WalletResponse.class);
    }

    private Wallet findWalletBy(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(
                        String.format("Customer with id %d not found", walletId)));

    }

    @Override
    public TransactionHistoryResponse getTransactionHistory(Long id) throws UserException {
     Optional<User>  foundUser = userRepository.findById(id);
     if (foundUser.isEmpty()){
         throw  new UserException("User not found");
     }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + beanConfig.getPaystackApiKey());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<TransactionHistoryResponse> response = restTemplate.exchange(beanConfig.getTransactionhistoryBaseUrl(), HttpMethod.GET, entity, TransactionHistoryResponse.class);

        return response.getBody();
    }
}
