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

@Slf4j

@SpringBootTest
class AppUserServiceImplTest {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private PaymentService paymentService;

    @Test
    public void testDurationFunctionality() {
        Duration daily = Duration.DAILY;
        long duration = appUserServiceImpl.fundDuration(daily);
        System.out.println(duration);
    }


    @Test
    public  void testInitial_Delay(){
        SaveRequest saveRequest = new SaveRequest();
        saveRequest.setHour(1);
        saveRequest.setMinutes(0);
        System.out.println(appUserServiceImpl.calculateInitialDelay(saveRequest));
    }
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



}