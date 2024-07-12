package com.gosave.gosave.services;

import com.gosave.gosave.data.model.BankAccount;
import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.BankAccountRepository;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.response.TransferResponse;
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
    @Autowired
    private AppUserServiceImpl appUserServiceImpl;


    @Override
    public TransferResponse addMoneyToWalletFromBank(AddMoneyRequest addMoneyRequest) {
        if(walletRepository.existsById(addMoneyRequest.getId())) throw new RuntimeException("\"wallet with id\" +walletRepository.findById(addMoneyRequest.getId())+ \"does not exist\" ");
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(addMoneyRequest.getAccountNumber());
        bankAccount.setBalance(addMoneyRequest.getAmount());
        bankAccount.setBankName(addMoneyRequest.getBankName());
        bankAccount.setId(addMoneyRequest.getId());
        BankAccount savedBankAcc = bankAccountRepository.save(bankAccount);
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setId(savedBankAcc.getId());

        return transferResponse;
    }

    @Override
    public BigDecimal getBalance(Long walletId) {
        BigDecimal balance = walletRepository.findById(walletId).get().getBalance();
        return balance;
    }

    @Override
    public Optional<Wallet> findWalletById(Long id) {
        return walletRepository.findById(id);
    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

}
