package com.gosave.gosave.services;

import com.gosave.gosave.data.model.BankAccount;
import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.BankAccountRepository;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    @Autowired
    private final WalletRepository walletRepository;

    @Autowired
    private final BankAccountRepository bankAccountRepository;



    @Override
    public TransferResponse addMoneyToWalletFromBank(AddMoneyRequest addMoneyRequest) {
        Optional<Wallet> foundWallet = walletRepository.findById(addMoneyRequest.getId());
        TransferResponse transferResponse = new TransferResponse();
        if(!foundWallet.isPresent()) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountNumber(addMoneyRequest.getAccountNumber());
            bankAccount.setBalance(addMoneyRequest.getAmount());
            bankAccount.setBankName(addMoneyRequest.getBankName());
            bankAccount.setId(addMoneyRequest.getId());
            bankAccountRepository.save(bankAccount);
            transferResponse.setId(foundWallet.get().getId());


        }else{
            throw new WalletNotFoundException("wallet with id" +walletRepository.findById(addMoneyRequest.getId())+ "does not exist");
        }

        return transferResponse;
    }

    @Override
    public BigDecimal getBalance(Long walletId) {
        Optional<Wallet> foundWallet = walletRepository.findById(walletId);
        BigDecimal balance = foundWallet.get().getBalance();
        return balance;
    }

}
