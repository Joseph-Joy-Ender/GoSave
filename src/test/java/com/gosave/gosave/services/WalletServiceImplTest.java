package com.gosave.gosave.services;

import com.gosave.gosave.data.repositories.WalletRepository;
import com.gosave.gosave.dto.request.AddMoneyRequest;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.TransferResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.WalletNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
//        addMoneyRequest.setDateTime(LocalDateTime.now());
        addMoneyRequest.setBankName("AccessBank");
        TransferResponse transferResponse = walletService.addMoneyToWalletFromBank(addMoneyRequest);
        assertNotNull(transferResponse);
    }

    @Test
    @Sql("/scripts/scripts.sql")
    public void testWalletBalanceAfterReceivingMoney()  {
 
       WalletResponse response = walletService.getBalance(101L);
       log.info("res-->{}", response);
        assertNotNull(response);
        System.out.println("The balance:: " + response.getBalance());

       BigDecimal balance = walletService.getBalance(301L);
       log.info("res-->{}", balance);
       
        assertNotNull(balance);
 
    }

    @Test
    @Sql("/scripts/scripts.sql")
    public  void testGet_CurrentBalance(){
        WalletRequest request = new WalletRequest();
        request.setId(3L);
        BigDecimal amount = BigDecimal.valueOf(2000);
        request.setBalance(BigDecimal.valueOf(2000));
        assertEquals(amount,walletService.getCurrentBalance(request));
    }

    @Test
    @Sql("/scripts/scripts.sql")
    public  void balanceIncreaseTest(){
        SaveRequest saveRequest = new SaveRequest();
        saveRequest.setId(3L);
        saveRequest.setAmount(BigDecimal.valueOf(2000));
        BigDecimal balance = walletService.addFundToWalletFromBank(saveRequest);
        BigDecimal expected = new BigDecimal("5000.00");
        assertEquals(expected,walletService.addFundToWalletFromBank(saveRequest));
    }

}