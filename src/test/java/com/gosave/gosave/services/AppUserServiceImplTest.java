package com.gosave.gosave.services;

import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserNotFoundException;
import com.gosave.gosave.exception.WalletExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@AllArgsConstructor
@Slf4j
public class AppUserServiceImplTest {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private PaymentService paymentService;


    @Test
    @Sql("/scripts/scripts.sql")
    public void testThatWalletCanSendMoneyMoneyTo() throws UserNotFoundException {
        ApiResponse<?> response = paymentService.transferFundsToWallet(201L);
        log.info("res-->{}", response);
        assertThat(response).isNotNull();
    }
    @Test
    public void testThatACustomerCanCreateWallet() throws WalletExistException {
        WalletRequest walletRequest = new WalletRequest();
        walletRequest.setId(100L);
        walletRequest.setBalance(BigDecimal.ZERO);
//        walletRequest.setTransaction
        WalletResponse walletResponse = appUserService.createWallet(walletRequest);
        assertThat(walletResponse).isNotNull();

    }

    @Test
    public void test() {

    }

}