package com.gosave.gosave.services;

import com.gosave.gosave.controller.BeanConfig;
import com.gosave.gosave.data.model.BankAccount;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.BankAccountRepository;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.request.InitializeTransactionRequest;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.PayStackTransactionResponse;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.exception.UserNotFoundException;
import com.gosave.gosave.exception.WalletNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BeanConfig beanConfig;



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
    public BigDecimal addFundToWalletFromBank(SaveRequest saveRequest) {
        TransferResponse response = new TransferResponse();
        ModelMapper mapper = new ModelMapper();
        Wallet mappedWallet = mapper.map(saveRequest, Wallet.class);
        Optional<Wallet> foundWalletOptional = walletRepository.findById(mappedWallet.getId());
        if (foundWalletOptional.isEmpty()) {
            throw new WalletNotFoundException("Wallet not found.");
        }
        Wallet foundWallet = foundWalletOptional.get();
        BigDecimal currentBalance = foundWallet.getBalance();
        BigDecimal addedAmount = saveRequest.getAmount();
        currentBalance = currentBalance.add(addedAmount);
        foundWallet.setBalance(currentBalance);
        walletRepository.save(foundWallet);
        response.setMessage(addedAmount + " has been added to your wallet");
        return currentBalance;
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

    @Override
    public BigDecimal getCurrentBalance(WalletRequest walletRequest) {
        ModelMapper mapper = new ModelMapper();
        Wallet wallet = mapper.map(walletRequest,Wallet.class);
        Optional<Wallet> foundWallet = walletRepository.findById( walletRequest.getId());
        if (foundWallet.isEmpty()){throw  new WalletNotFoundException("Wallet not found") ; }
        return wallet.getBalance();
    }

}
