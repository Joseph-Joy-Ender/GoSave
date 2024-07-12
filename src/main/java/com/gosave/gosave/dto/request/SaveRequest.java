package com.gosave.gosave.dto.request;

import com.gosave.gosave.data.model.Duration;
import com.gosave.gosave.data.model.TimePeriod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class SaveRequest {
    private Duration duration;
    private TimeRequest timeRequest;
    private BigDecimal amount;
}
