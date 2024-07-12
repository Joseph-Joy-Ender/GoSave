package com.gosave.gosave.services;

import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserNotFoundException;
import com.gosave.gosave.exception.WalletNotFoundException;

public interface PaymentService {
    ApiResponse<?> transferFundsToWallet(Long userId) throws UserNotFoundException;

    WalletResponse getBalance(Long walletId) throws WalletNotFoundException;


}
