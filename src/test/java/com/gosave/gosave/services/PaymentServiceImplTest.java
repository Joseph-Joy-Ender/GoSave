package com.gosave.gosave.services;

import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
public class PaymentServiceImplTest {
    @Autowired
    private PaymentService paymentService;

    @Test
    @Sql("/scripts/scripts.sql")
    public void testThatWalletCanReceiveMoneyFrom () throws UserNotFoundException {
        ApiResponse<?> response = paymentService.transferFundsToWallet(101L);
        log.info("res-->{}", response);
        System.out.println(response.getData());
        assertThat(response).isNotNull();
    }
    @Test
    @Sql("/scripts/scripts.sql")
    public void testWalletBalanceAfterReceivingMoney() {
        WalletResponse response = paymentService.getBalance(101L);
        log.info("res-->{}", response);
        assertNotNull(response);
        System.out.println("The balance:: " + response.getBalance());
    }

}