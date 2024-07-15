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
<<<<<<< HEAD
    private LocalDateTime dateTime;
    private BigDecimal balance;
=======
//    private LocalDateTime dateTime;
>>>>>>> a8ef42e6f5169a08cf763d3d4fe09d1c01861450
}
