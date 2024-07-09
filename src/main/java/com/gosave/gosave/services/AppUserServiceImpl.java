package com.gosave.gosave.services;
import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.dto.request.SaveRequest;
import com.gosave.gosave.dto.request.TimeRequest;
import com.gosave.gosave.dto.response.SaveResponse;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class AppUserServiceImpl implements AppUserService {
    @Override
    public SaveResponse saveFund(SaveRequest saveRequest) {
        saveFundDuration(saveRequest.getDuration()) ;
        saveFundTimePeriod(saveRequest.getTimeRequest());
        return null;
    }
    public long saveFundDuration(Duration duration){
        return switch (duration) {
            case DAILY -> 24L * 60 * 60 * 1000;
            case WEEKLY -> 7L * 24 * 60 * 60 * 1000;
            case MONTHLY -> 30L * 24 * 60 * 60 * 1000;
            default -> throw new IllegalArgumentException("Unknown duration: " + duration);
        };
    }

    public LocalTime saveFundTimePeriod(TimeRequest timeRequest){
        return LocalTime.of(timeRequest.getHour(), timeRequest.getMinutes());
    }
}
