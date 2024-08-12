package com.gosave.gosave.services;
import com.gosave.gosave.config.BeanConfig;
import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.dto.response.WalletResponse;
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
    private final BeanConfig beanConfig;
    private final ModelMapper mapper = new ModelMapper();



    @Override
    public BigDecimal addFundToWalletFromBank(SaveRequest saveRequest) {
        TransferResponse response = new TransferResponse();
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


//    @Override
//    public BigDecimal getBalance(Long walletId) {
//        BigDecimal balance = walletRepository.findById(walletId).get().getBalance();
//        return balance; }
     @Override
    public WalletResponse getBalance(Long walletId) throws WalletNotFoundException {
        return mapper.map(findWalletBy(walletId), WalletResponse.class);
    }

    private Wallet findWalletBy(Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(
                        String.format("Wallet with id %d not found", walletId)));
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

