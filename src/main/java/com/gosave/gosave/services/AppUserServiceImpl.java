package com.gosave.gosave.services;


import com.gosave.gosave.controller.BeanConfig;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.UserRepository;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.InitializeTransactionRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.PayStackTransactionResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserNotFoundException;
import com.gosave.gosave.exception.WalletExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private final WalletRepository walletRepository;




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


}


