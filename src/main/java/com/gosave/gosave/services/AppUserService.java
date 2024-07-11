package com.gosave.gosave.services;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserNotFoundException;
import com.gosave.gosave.exception.WalletExistException;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.response.SaveResponse;

public interface AppUserService {
    SaveResponse saveFund(SaveRequest saveRequest);

    WalletResponse createWallet(WalletRequest walletRequest) throws WalletExistException;


    ApiResponse<?> transferFundsToWallet(Long userId);

}




