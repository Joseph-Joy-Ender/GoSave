package com.gosave.gosave.services;
import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.exception.UserNotFoundException;
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

@SpringBootTest
@Slf4j
public class AppUserServiceImplTest {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private AppUserServiceImpl appUserServiceImpl;

    @Test
    public void testDurationFunctionality() {
        Duration daily = Duration.DAILY;
        long duration = appUserServiceImpl.fundDuration(daily);
        System.out.println(duration);
    }


//    @Test
//    public void testThatUserCanCreateAccount() throws UserException {
//        CreateAccountRequest accountRequest = new CreateAccountRequest();
//        accountRequest.setUsername("Udeme Chloe");
//        accountRequest.setEmail("udeme5017@gmail.com");
//        accountRequest.setPassword("udeme5017");
//        CreateAccountResponse response = appUserService.createAccount(accountRequest);
//        assertThat(response).isNotNull();
//        assertThat("udeme5017@gmail.com").isEqualTo(accountRequest.getEmail());
//    }
//    @Test
//    public void testThatExceptionIsThrownIfUserExist() throws UserException {
//        CreateAccountRequest accountRequest = new CreateAccountRequest();
//        accountRequest.setUsername("Udeme Chloe");
//        accountRequest.setEmail("udeme5017@gmail.com");
//        accountRequest.setPassword("udeme5017");
//        assertThat("udeme5017@gmail.com").isEqualTo(accountRequest.getEmail());
//        assertThatExceptionOfType(UserException.class).isThrownBy(() ->appUserService.createAccount(accountRequest));
//    }

    @Test
    @Sql("/scripts/scripts.sql")
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
        public void testThatACustomerCanCreateWallet () throws WalletExistException {
            WalletRequest walletRequest = new WalletRequest();
            walletRequest.setId(100L);
            walletRequest.setBalance(BigDecimal.ZERO);
//          walletRequest.setTransaction
            WalletResponse walletResponse = appUserService.createWallet(walletRequest);
            assertThat(walletResponse).isNotNull();

        }



}