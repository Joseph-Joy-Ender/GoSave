package com.gosave.gosave.services;

import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletNotFoundException;

import java.math.BigDecimal;

public interface   WalletService {

    TransferResponse addMoneyToWalletFromBank(AddMoneyRequest addMoneyRequest);

    WalletResponse getBalance(Long walletId) throws WalletNotFoundException;
}
