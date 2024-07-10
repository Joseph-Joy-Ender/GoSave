package com.gosave.gosave.services;

import com.gosave.gosave.data.model.Wallet;
import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.exception.WalletNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class WalletServiceImplTest {
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void testThatMoneyCanBeAddedToWallet() throws WalletNotFoundException {
        AddMoneyRequest addMoneyRequest = new AddMoneyRequest();
        addMoneyRequest.setId(300L);
        addMoneyRequest.setAmount(BigDecimal.ZERO);
        addMoneyRequest.setAccountNumber("1234567890");
        addMoneyRequest.setDateTime(LocalDateTime.now());
        addMoneyRequest.setBankName("AccessBank");
        TransferResponse transferResponse = walletService.addMoneyToWalletFromBank(addMoneyRequest);
        assertNotNull(transferResponse);
    }

    @Test
    @Sql("/scripts/scripts.sql")
    public void testWalletBalanceAfterReceivingMoney()  {
       BigDecimal balance = walletService.getBalance(301L);
       log.info("res-->{}", balance);
        assertNotNull(balance);
    }

}