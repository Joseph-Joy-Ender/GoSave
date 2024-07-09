package com.gosave.gosave;

import com.gosave.gosave.services.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class GoSaveApplicationTests {
    @Autowired
    private final AppUserService appUserService;

    GoSaveApplicationTests(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Test
    public void testThatUserCanSendMoneyTo() {
        SendRequest sendRequest = new SendRequest("1234567890","1234512345",
                5000.00,"1234-34422332344vx","Oluwatobi ojo");
        appUserService.transferFunds(sendRequest);
        assertNotNull()
    }

}
