package com.gosave.gosave.services;

import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.dto.response.WalletResponse;

import java.math.BigDecimal;

public interface   WalletService {

    TransferResponse addMoneyToWalletFromBank(AddMoneyRequest addMoneyRequest);

    BigDecimal getBalance(Long walletId);
}
