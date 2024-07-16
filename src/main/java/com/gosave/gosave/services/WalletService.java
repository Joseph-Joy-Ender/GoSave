
package com.gosave.gosave.services;

import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

public interface   WalletService {

    BigDecimal addFundToWalletFromBank (SaveRequest saveRequest);
    WalletResponse getBalance(Long walletId) throws WalletNotFoundException;
    Optional<Wallet> findWalletById(Long id);
    Wallet save(Wallet wallet);
    BigDecimal getCurrentBalance (WalletRequest walletRequest);

}
