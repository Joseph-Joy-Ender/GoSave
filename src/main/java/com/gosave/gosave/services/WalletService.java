package com.gosave.gosave.services;

import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.response.TransferResponse;

import java.math.BigDecimal;

public interface   WalletService {

    TransferResponse addMoneyToWalletFromBank(AddMoneyRequest addMoneyRequest);

    BigDecimal getBalance(Long walletId);
}
