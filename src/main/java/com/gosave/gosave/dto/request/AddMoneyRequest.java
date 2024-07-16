package com.gosave.gosave.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AddMoneyRequest {
    private Long id;
    private BigDecimal balance;
    private BigDecimal amount;
    private String bankName;
    private String accountNumber;
    private LocalDateTime dateTime;

}
