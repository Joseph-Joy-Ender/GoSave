
package com.gosave.gosave.services;

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

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class WalletServiceImplTest {
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletRepository walletRepository;


//    @Test
//    @Sql("/scripts/scripts.sql")
//    public void testWalletBalanceAfterReceivingMoney()  {
//
//       WalletResponse response = walletService.getBalance(101L);
//       log.info("res-->{}", response);
//        assertNotNull(response);
//        System.out.println("The balance:: " + response.getBalance());
//
//       BigDecimal balance = walletService.getBalance(301L);
//       log.info("res-->{}", balance);
//
//        assertNotNull(balance);
//
//    }

    @Test
    @Sql("/scripts/scripts.sql")
    public  void testGet_CurrentBalance(){
        WalletRequest request = new WalletRequest();
        request.setId(3L);
        BigDecimal amount = BigDecimal.valueOf(2000);
        request.setBalance(BigDecimal.valueOf(2000));
        assertEquals(amount,walletService.getCurrentBalance(request));
    }

}