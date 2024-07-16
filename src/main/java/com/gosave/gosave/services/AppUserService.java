package com.gosave.gosave.services;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.SaveResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletExistException;


public interface AppUserService {

    SaveResponse save(SaveRequest saveRequest);

    WalletResponse createWallet(WalletRequest walletRequest) throws WalletExistException;



}




