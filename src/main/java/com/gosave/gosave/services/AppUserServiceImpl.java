package com.gosave.gosave.services;


import com.gosave.gosave.config.BeanConfig;
import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.SaveResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.gosave.gosave.data.model.Wallet;

import com.gosave.gosave.data.repositories.WalletRepository;

import com.gosave.gosave.data.repositories.UserRepository;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.CreateAccountRequest;
import com.gosave.gosave.dto.request.InitializeTransactionRequest;

import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.CreateAccountResponse;
import com.gosave.gosave.dto.response.PayStackTransactionResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.exception.WalletExistException;
import com.gosave.gosave.utils.GenerateApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    private final PaymentService paymentService;
    private final WalletService walletService;
    private final UserRepository userRepository;
    private final BeanConfig beanConfig;
    private final ModelMapper mapper = new ModelMapper();
//    private PasswordEncoder passwordEncoder;



    @Override
    public SaveResponse save(SaveRequest saveRequest) {
        SaveResponse saveResponse = new SaveResponse();
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    withdrawFromAccount(saveRequest);
                } catch (UserException | UserNotFoundException | WalletExistException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(timerTask, calculateInitialDelay(saveRequest));
        saveResponse.setMessage("Hello " +saveRequest.getUsername()+
                " the amount of  "+saveRequest.getAmount()
                + " have been added to your wallet,  your current wallet balance is   "
                +saveRequest.getBalance());
        return saveResponse;
    }




    public SaveResponse withdrawFromAccount(SaveRequest saveRequest) throws UserException, WalletExistException, UserNotFoundException {
         ModelMapper mapper = new ModelMapper();
        SaveResponse saveResponse = new SaveResponse();
        User mappedUser = mapper.map(saveRequest,User.class) ;
        Wallet mappedWallet = mapper.map(saveRequest,Wallet.class);
        User foundUser  = userRepository.findByUsername(mappedUser.getUsername());
        Optional<Wallet> foundWallet = walletService.findWalletById(mappedWallet.getId());
        if (foundUser == null )    {throw new UserException("User does not exist") ;}
        if (foundWallet.isEmpty()) {throw new WalletExistException("Wallet does not exist");}
        fundDuration(saveRequest.getDuration()) ;
        calculateInitialDelay(saveRequest);
        ApiResponse<?>  response = paymentService.transferFundsToWallet(saveRequest.getId());
        if (response != null){
            System.out.println(response);
            walletService.addFundToWalletFromBank(saveRequest);
            saveResponse.setMessage("Hello your savings have been made");
        }

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

//    @Override
    public ApiResponse<?> transferFundsToWallet(Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        Optional<User> foundUser = userRepository.findById(userId);
        HttpEntity<InitializeTransactionRequest> request = buildPaymentRequest(foundUser);
        ResponseEntity<PayStackTransactionResponse> response =
                restTemplate.postForEntity(beanConfig.getPaystackBaseUrl(), request, PayStackTransactionResponse.class);
        return new ApiResponse<>(response.getBody());
    }
    @Override
    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }


    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest accountRequest) throws UserException {
        if (userRepository.existsByEmail(accountRequest.getEmail())) throw new UserException(GenerateApiResponse.ACCOUNT_WITH_THIS_EMAIL_ALREADY_EXIST);
        User user = mapper.map(accountRequest, User.class);
//        user.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        userRepository.save(user);
        return GenerateApiResponse.create(GenerateApiResponse.REGISTER_SUCCESSFUL);
    }

    private HttpEntity<InitializeTransactionRequest> buildPaymentRequest(Optional<User> foundUser) {
        InitializeTransactionRequest transactionRequest = new InitializeTransactionRequest();
        transactionRequest.setEmail(foundUser.get().getEmail());
        transactionRequest.setAmount(foundUser.get().getAmount());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer "+beanConfig.getPaystackApiKey());
        return new HttpEntity<>(transactionRequest, headers);
    }
}


