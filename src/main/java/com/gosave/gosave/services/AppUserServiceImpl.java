package com.gosave.gosave.services;


import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.SaveResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    private final WalletRepository walletRepository;


    @Override
    public SaveResponse saveFund(SaveRequest saveRequest) {
        return null;
    }

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


