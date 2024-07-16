package com.gosave.gosave.services;

import com.gosave.gosave.dto.request.CreateAccountRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.CreateAccountResponse;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.dto.request.*;
import com.gosave.gosave.dto.response.SaveResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.exception.WalletExistException;

import java.util.Optional;


public interface AppUserService {

    SaveResponse save(SaveRequest saveRequest);

    WalletResponse createWallet(WalletRequest walletRequest) throws WalletExistException;
    ApiResponse<?> transferFundsToWallet(Long userId);
    CreateAccountResponse createAccount(CreateAccountRequest accountRequest) throws UserException;

    Optional<User> findUser (Long id);


}
