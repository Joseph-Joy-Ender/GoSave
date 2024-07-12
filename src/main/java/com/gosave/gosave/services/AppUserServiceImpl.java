package com.gosave.gosave.services;

import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.UserRequest;
import com.gosave.gosave.dto.response.SaveResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.gosave.gosave.controller.BeanConfig;
import com.gosave.gosave.data.model.Wallet;

import com.gosave.gosave.data.repositories.WalletRepository;

import com.gosave.gosave.data.repositories.UserRepository;

import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import java.math.BigDecimal;
 
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service
@AllArgsConstructor
@Slf4j

public class AppUserServiceImpl implements AppUserService {
    private final WalletService walletService;
    private final UserRepository userRepository;
    private final BeanConfig beanConfig;
    private final PaymentServiceImpl paymentServiceImpl;

    @Override
    public SaveResponse save(SaveRequest saveRequest) {
        SaveResponse saveResponse = new SaveResponse();
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    initiateTransaction(saveRequest);
                } catch (UserException | UserNotFoundException | WalletExistException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(timerTask, calculateInitialDelay(saveRequest));
        saveResponse.setMessage("Money has been saved");
        return saveResponse;
    }




    public SaveResponse initiateTransaction(SaveRequest saveRequest) throws UserException, WalletExistException, UserNotFoundException {
        SaveResponse saveResponse = new SaveResponse();
        WalletRequest walletRequest = new WalletRequest();
        User foundUser  = userRepository.findByUsername(saveRequest.getUsername());
        Optional<Wallet> foundWallet = walletService.findWalletById(saveRequest.getWallet_id());
        if (foundUser == null )    {throw new UserException("User does not exist") ;}
        if (foundWallet.isEmpty()) {throw new WalletExistException("Wallet does not exist");}
        fundDuration(saveRequest.getDuration()) ;
        calculateInitialDelay(saveRequest);
        BigDecimal currentBalance = walletService.getCurrentBalance(walletRequest);
      
            saveResponse.setMessage("");
        return saveResponse;
    }
    
    public long fundDuration(Duration duration){
        return switch (duration) {
            case DAILY -> 24L * 60 * 60 * 1000;
            case WEEKLY -> 7L * 24 * 60 * 60 * 1000;
            case MONTHLY -> 30L * 24 * 60 * 60 * 1000;
            default -> throw new IllegalArgumentException("Unknown duration: " + duration);
        };
    }
    public  long calculateInitialDelay(SaveRequest saveRequest) {
        LocalDateTime now = java.time.LocalDateTime.now();
        LocalDateTime nextExecution = now.withHour(saveRequest.getHour()).withMinute(saveRequest.getMinutes());
        if (now.isAfter(nextExecution)) {
            nextExecution = nextExecution.plusDays(1);
        }
        java.time.Duration duration = java.time.Duration.between(now, nextExecution);
        return duration.toMillis();
    }


    @Override
    public WalletResponse createWallet(WalletRequest walletRequest) throws WalletExistException {
        Optional<Wallet> foundWallet = walletService.findWalletById(walletRequest.getId());
        WalletResponse walletResponse = new WalletResponse();
        if(!foundWallet.isPresent()) {
            Wallet wallet = new Wallet();
            wallet.setId(walletRequest.getId());
            wallet.setBalance(walletRequest.getBalance());
            walletService.save(wallet);
            walletResponse.setId(wallet.getId());
        }else{
            throw new WalletExistException("wallet with id" +walletService.findWalletById(walletRequest.getId())+ "already exist");
        }
        return walletResponse;
    }

   
    public User getCurrentUser(String request) throws UserException {
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(request,User.class);
    User  foundUser = userRepository.findByUsername(user.getUsername()) ;
    if (foundUser == null){
        throw  new UserException("User not found");
    }
        return user;
    }
   


  



}


