package com.gosave.gosave.services;

import com.gosave.gosave.dto.request.CreateAccountRequest;
import com.gosave.gosave.dto.request.WalletRequest;
import com.gosave.gosave.dto.response.ApiResponse;
import com.gosave.gosave.dto.response.CreateAccountResponse;
import com.gosave.gosave.dto.response.WalletResponse;
import com.gosave.gosave.exception.UserException;
import com.gosave.gosave.exception.WalletExistException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Slf4j
public class AppUserServiceImplTest {
    @Autowired
    private AppUserService appUserService;


    @Test
    public void testThatUserCanCreateAccount() throws UserException {
        CreateAccountRequest accountRequest = new CreateAccountRequest();
        accountRequest.setUsername("Udeme Chloe");
        accountRequest.setEmail("udeme5017@gmail.com");
        accountRequest.setPassword("udeme5017");
        CreateAccountResponse response = appUserService.createAccount(accountRequest);
        assertThat(response).isNotNull();
        assertThat("udeme5017@gmail.com").isEqualTo(accountRequest.getEmail());
    }
    @Test
    public void testThatExceptionIsThrownIfUserExist() throws UserException {
        CreateAccountRequest accountRequest = new CreateAccountRequest();
        accountRequest.setUsername("Udeme Chloe");
        accountRequest.setEmail("udeme5017@gmail.com");
        accountRequest.setPassword("udeme5017");
        assertThat("udeme5017@gmail.com").isEqualTo(accountRequest.getEmail());
        assertThatExceptionOfType(UserException.class).isThrownBy(() ->appUserService.createAccount(accountRequest));
    }

    @Test
    @Sql("/scripts/scripts.sql")
    public void testThatWalletCanSendMoneyMoneyTo() {
        ApiResponse<?> response = appUserService.transferFundsToWallet(201L);
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