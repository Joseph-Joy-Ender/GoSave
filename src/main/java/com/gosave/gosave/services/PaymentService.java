package com.gosave.gosave.services;

import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.TransactionHistoryResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.exception.UserNotFoundException;

public interface PaymentService {
    ApiResponse<?> transferFundsToWallet(Long userId) throws UserNotFoundException;
    TransactionHistoryResponse getTransactionHistory (Long id) throws UserException;
}
