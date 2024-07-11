package com.gosave.gosave.services;
import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.TimeRequest;
import com.gosave.gosave.dto.response.SaveResponse;
import org.springframework.stereotype.Service;
import com.gosave.gosave.controller.BeanConfig;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.UserRepository;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.InitializeTransactionRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.time.LocalDateTime;
import java.util.Optional;
import java.time.LocalTime;
@Service
@AllArgsConstructor
@Slf4j

public class AppUserServiceImpl implements AppUserService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final BeanConfig beanConfig;

    @Override
    public SaveResponse saveFund(SaveRequest saveRequest) {
        saveFundDuration(saveRequest.getDuration()) ;
        saveFundTimePeriod(saveRequest.getTimeRequest());
        return null;
    }
    public long saveFundDuration(Duration duration){
        return switch (duration) {
            case DAILY -> 24L * 60 * 60 * 1000;
            case WEEKLY -> 7L * 24 * 60 * 60 * 1000;
            case MONTHLY -> 30L * 24 * 60 * 60 * 1000;
            default -> throw new IllegalArgumentException("Unknown duration: " + duration);
        };
    }
    private static long calculateInitialDelay(TimeRequest timeRequest) {
        LocalDateTime now = java.time.LocalDateTime.now();
        LocalDateTime nextExecution = now.withHour(timeRequest.getHour()).withMinute(timeRequest.getMinutes());
        // If the next execution time is in the past, add one day
        if (now.compareTo(nextExecution) > 0) {
            nextExecution = nextExecution.plusDays(1);
        }

        // Calculate the delay in milliseconds
        java.time.Duration duration = java.time.Duration.between(now, nextExecution);
        return duration.toMillis();
    }

    public LocalTime saveFundTimePeriod(TimeRequest timeRequest){
        return LocalTime.of(timeRequest.getHour(), timeRequest.getMinutes());
    }

    public void withdrawFund (){
        
    }


    @Override
    public WalletResponse createWallet(WalletRequest walletRequest) throws WalletExistException {
        Optional<Wallet> foundWallet = walletRepository.findById(walletRequest.getId());
        WalletResponse walletResponse = new WalletResponse();
        if(!foundWallet.isPresent()) {
            Wallet wallet = new Wallet();
            wallet.setId(walletRequest.getId());
            wallet.setBalance(walletRequest.getBalance());
            walletRepository.save(wallet);
            walletResponse.setId(wallet.getId());
        }else{
            throw new WalletExistException("wallet with id" +walletRepository.findById(walletRequest.getId())+ "already exist");
        }
        return walletResponse;
    }

    @Override
    public ApiResponse<?> transferFundsToWallet(Long userId) {
        return null;
    }

   


    private HttpEntity<InitializeTransactionRequest> buildPaymentRequest(Optional<User> foundUser) {
        return getInitializeTransactionRequestHttpEntity(foundUser, beanConfig);

    }

    static HttpEntity<InitializeTransactionRequest> getInitializeTransactionRequestHttpEntity(Optional<User> foundUser, BeanConfig beanConfig) {
        InitializeTransactionRequest transactionRequest = new InitializeTransactionRequest();
        transactionRequest.setEmail(foundUser.get().getEmail());
        transactionRequest.setAmount(foundUser.get().getAmount());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+ beanConfig.getPaystackApiKey());
        return new HttpEntity<>(transactionRequest, headers);
    }

}


