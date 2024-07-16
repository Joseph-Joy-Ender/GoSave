package com.gosave.gosave.services;
import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.data.repositories.UserRepository;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.UserRequest;
import com.gosave.gosave.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j

@SpringBootTest
class AppUserServiceImplTest {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserRepository userRepository;
//    @AfterEach
//    public  void  deleteData(){
//        userRepository.deleteAll();
//    }
//    @BeforeEach
//    public  void deleteAllData(){
//        userRepository.deleteAll();
//    }

    @Test
         public void testSave_Functionality(){
          SaveRequest saveRequest = new SaveRequest();
          saveRequest.setHour(1);
          saveRequest.setMinutes(0);
          saveRequest.setUsername("joyender");
          saveRequest.setDuration(Duration.DAILY);
          saveRequest.setId(3L);
          saveRequest.setAmount(BigDecimal.valueOf(1000));
          saveRequest.setBankName("Sterling");
          saveRequest.setAccountNumber("0624808087");
             BigDecimal expected = new BigDecimal("2000.00");
             assertEquals(expected,walletService.addFundToWalletFromBank(saveRequest));
             System.out.println(appUserService.save(saveRequest).getMessage());
         }
        @Test
        @Sql("/scripts/scripts.sql")
        public void testThatWalletCanSendMoneyMoneyTo () throws UserNotFoundException {
            ApiResponse<?> response = paymentService.transferFundsToWallet(201L);
            log.info("res-->{}", response);
            System.out.println(response.getData());
            assertThat(response).isNotNull();
        }

        @Test
        public void thatUserCanRegister(){
            UserRequest userRequest = new UserRequest();
            userRequest.setUsername("Joan Hudson");
            userRequest.setPassword("joan,1234");
            userRequest.setEmail("joan340@gmail.com");
            assertNotNull(appUserService.registerUser(userRequest));

        }

    @Test
    public void testThat_AnotherUser_CanRegister(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Anna Jacobs");
        userRequest.setPassword("anna,1234");
        userRequest.setEmail("anna@gmail.com");
        assertNotNull(appUserService.registerUser(userRequest));
        assertEquals(3,userRepository.count());


    }


    @Test
    public void testThat_ARegisteredUser_HasAWallet(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("James Kenneth");
        userRequest.setPassword("james,1234");
        userRequest.setEmail("james@gmail.com");
        assertNotNull(appUserService.registerUser(userRequest));
        assertEquals(4,userRepository.count());

    }


    @Test
    public void testThat_ARegisteredUser_HasAWalletAgain(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("Precious Etim");
        userRequest.setPassword("pressy,1234");
        userRequest.setEmail("pressy@gmail.com");
        userRequest.setAmount(BigDecimal.valueOf(2000));
        userRequest.setDuration(Duration.DAILY);
        assertNotNull(appUserService.registerUser(userRequest));
        assertEquals(5,userRepository.count());

    }

        @Test
        public void testThatACustomerCanCreateWallet () throws WalletExistException {
            WalletRequest walletRequest = new WalletRequest();
            walletRequest.setId(100L);
            walletRequest.setBalance(BigDecimal.ZERO);
//          walletRequest.setTransaction
            WalletResponse walletResponse = appUserService.createWallet(walletRequest);
            assertThat(walletResponse).isNotNull();

        }



}