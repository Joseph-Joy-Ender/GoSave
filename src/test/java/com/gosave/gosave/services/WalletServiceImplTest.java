
package com.gosave.gosave.services;

import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
@AllArgsConstructor
public class WalletServiceImplTest {
    private WalletServiceImpl walletService;

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
