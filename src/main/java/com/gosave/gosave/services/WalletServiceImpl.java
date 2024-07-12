package com.gosave.gosave.services;

import com.gosave.gosave.data.model.BankAccount;
import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.BankAccountRepository;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class    WalletServiceImpl implements WalletService {
    @Autowired
    private final WalletRepository walletRepository;

    @Autowired
    private final BankAccountRepository bankAccountRepository;
    @Autowired
    private final ModelMapper mapper = new ModelMapper();



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
    public WalletResponse getBalance(Long walletId) throws WalletNotFoundException {
        return mapper.map(findWalletBy(walletId), WalletResponse.class);
    }

    private Wallet findWalletBy(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(
                        String.format("Customer with id %d not found", walletId)));
    }




}
