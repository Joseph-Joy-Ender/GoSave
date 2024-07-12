package com.gosave.gosave.services;


import com.gosave.gosave.config.BeanConfig;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.UserRepository;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.CreateAccountRequest;
import com.gosave.gosave.dto.request.InitializeTransactionRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.CreateAccountResponse;
import com.gosave.gosave.dto.response.PayStackTransactionResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.exception.WalletExistException;
import com.gosave.gosave.utils.GenerateApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private final WalletRepository walletRepository;
    @Autowired
    private final UserRepository userRepository;
    private final BeanConfig beanConfig;
    private final ModelMapper mapper = new ModelMapper();
    private PasswordEncoder passwordEncoder;



    @Override
    public WalletResponse createWallet(WalletRequest walletRequest) throws WalletExistException {
        Optional<Wallet> foundWallet = walletRepository.findById(walletRequest.getId());
        WalletResponse walletResponse = new WalletResponse();
        if(!foundWallet.isPresent()) {
            Wallet wallet = new Wallet();
            wallet.setId(walletRequest.getId());
            wallet.setBalance(walletRequest.getBalance());
            walletRepository.save(wallet);
            walletResponse.setId(wallet.getId());
        }else{
            throw new WalletExistException("wallet with id" +walletRepository.findById(walletRequest.getId())+ "already exist");
        }
        return walletResponse;
    }

    @Override
    public ApiResponse<?> transferFundsToWallet(Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        Optional<User> foundUser = userRepository.findById(userId);
        HttpEntity<InitializeTransactionRequest> request = buildPaymentRequest(foundUser);
        ResponseEntity<PayStackTransactionResponse> response =
                restTemplate.postForEntity(beanConfig.getPaystackBaseUrl(), request, PayStackTransactionResponse.class);
        return new ApiResponse<>(response.getBody());
    }

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest accountRequest) throws UserException {
        if (userRepository.existsByEmail(accountRequest.getEmail())) throw new UserException(GenerateApiResponse.ACCOUNT_WITH_THIS_EMAIL_ALREADY_EXIST);
        User user = mapper.map(accountRequest, User.class);
        user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        userRepository.save(user);
        return GenerateApiResponse.create(GenerateApiResponse.REGISTER_SUCCESSFUL);
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


