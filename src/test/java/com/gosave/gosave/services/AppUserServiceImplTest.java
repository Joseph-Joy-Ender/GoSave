package com.gosave.gosave.services;
import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.dto.request.TimeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppUserServiceImplTest {
   @Autowired
   private AppUserServiceImpl appUserService;
    @Test
    public void testDurationFunctionality(){
        Duration daily = Duration.DAILY;
        long  duration= appUserService.saveFundDuration(daily);
      System.out.println(duration);
    }

    @Test
    public  void  testTime_Functionality(){
        TimeRequest timeRequest = new TimeRequest();
        timeRequest.setHour(1);
        timeRequest.setMinutes(30);
        System.out.println(appUserService.saveFundTimePeriod(timeRequest));
    }

}