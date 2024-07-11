package com.gosave.gosave.services;
import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.dto.request.TimeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@AllArgsConstructor
@Slf4j

@SpringBootTest
class AppUserServiceImplTest {
    @Autowired
    private AppUserService appUserService;
    @Autowired
   private AppUserServiceImpl appUserServiceImpl;

    @Test
    public void testDurationFunctionality(){
        Duration daily = Duration.DAILY;
        long  duration= appUserServiceImpl.saveFundDuration(daily);
      System.out.println(duration);
    }

    @Test
    public  void  testTime_Functionality() throws WalletExistException {
        TimeRequest timeRequest = new TimeRequest();
        timeRequest.setHour(1);
        timeRequest.setMinutes(30);
        System.out.println(appUserServiceImpl.saveFundTimePeriod(timeRequest));    }


        @Test
        @Sql("/scripts/scripts.sql")
        public void testThatWalletCanSendMoneyMoneyTo () {
            ApiResponse<?> response = appUserService.transferFundsToWallet(201L);
            log.info("res-->{}", response);
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