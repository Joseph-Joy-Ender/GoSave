package com.gosave.gosave.services;

import com.gosave.gosave.dto.request.CreateAccountRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.CreateAccountResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.exception.WalletExistException;

public interface AppUserService {
    WalletResponse createWallet(WalletRequest walletRequest) throws WalletExistException;
    ApiResponse<?> transferFundsToWallet(Long userId);
    CreateAccountResponse createAccount(CreateAccountRequest accountRequest) throws UserException;

}
