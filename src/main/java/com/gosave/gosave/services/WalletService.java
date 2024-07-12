package com.gosave.gosave.services;

import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.response.TransferResponse;

import java.math.BigDecimal;
import java.util.Optional;

public interface   WalletService {

    TransferResponse addMoneyToWalletFromBank(AddMoneyRequest addMoneyRequest);

    BigDecimal getBalance(Long walletId);
    Optional<Wallet> findWalletById(Long id);
    Wallet save(Wallet wallet);
}
