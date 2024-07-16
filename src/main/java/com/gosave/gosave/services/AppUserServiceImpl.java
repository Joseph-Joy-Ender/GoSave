package com.gosave.gosave.services;
import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.data.model.User;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.UserRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.SaveResponse;
import com.gosave.gosave.dto.response.UserResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.UserRepository;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

     @Override
     public UserResponse registerUser(UserRequest userRequest) {
       ModelMapper mapper = new ModelMapper();
       UserResponse response =  new UserResponse();
       User mappedUser =  mapper.map(userRequest,User.class) ;
       User   foundUser = userRepository.findByUsername(mappedUser.getUsername()) ;
       if (foundUser != null){throw new UserException("User with username "+mappedUser.getUsername() +" already  exist .");}
       userRepository.save(mappedUser);
         Wallet mappedWallet = mapper.map(userRequest, Wallet.class);
         mappedWallet.setUser(mappedUser);
         walletService.save(mappedWallet);
         response.setMessage("User with the username "+mappedUser.getUsername()+" Has been successfully  registered .");
         return response;
     }
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
                + " has been added to your wallet,  your current wallet balance is >>>>>>>>  "
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
        ApiResponse<?>  response = paymentService.transferMoneyToWallet(saveRequest);
        if (response != null){
            log.info(">>>>>>>>",response);
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

    


}


