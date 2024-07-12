
package com.gosave.gosave.services;
import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.TimeRequest;
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
@Slf4j

@SpringBootTest
class AppUserServiceImplTest {
    @Autowired
    private AppUserService appUserService;
    @Autowired
   private AppUserServiceImpl appUserServiceImpl;
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
//        walletRequest.setTransaction
            WalletResponse walletResponse = appUserService.createWallet(walletRequest);
            assertThat(walletResponse).isNotNull();

        }



}