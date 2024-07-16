
package com.gosave.gosave.services;

import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.TransferResponse;

import com.gosave.gosave.exception.WalletNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ModelMapper mapper = new ModelMapper();


    @Override
    public TransferResponse addMoneyToWalletFromBank(AddMoneyRequest addMoneyRequest) {
        if (walletRepository.existsById(addMoneyRequest.getId()))
            throw new RuntimeException("\"wallet with id\" +walletRepository.findById(addMoneyRequest.getId())+ \"does not exist\" ");
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
    public Optional<Wallet> findWalletById(Long id) {
        return walletRepository.findById(id);
    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public BigDecimal getCurrentBalance(WalletRequest walletRequest) {
        ModelMapper mapper = new ModelMapper();
        Wallet wallet = mapper.map(walletRequest, Wallet.class);
        Optional<Wallet> foundWallet = walletRepository.findById(walletRequest.getId());
        if (foundWallet.isEmpty()) {
            throw new WalletNotFoundException("Wallet not found");
        }
        return wallet.getBalance();
    }


}