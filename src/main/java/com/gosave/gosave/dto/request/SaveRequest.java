package com.gosave.gosave.dto.request;

import com.gosave.gosave.data.model.Duration;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class SaveRequest {
    private Duration duration;
    private String username;
    private int hour;
    private int minutes;
    private Long id;
    private BigDecimal amount;
    private String bankName;
    private String accountNumber;
    private BigDecimal balance;
}
    


