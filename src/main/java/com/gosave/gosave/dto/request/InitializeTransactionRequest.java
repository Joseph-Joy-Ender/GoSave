package com.gosave.gosave.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InitializeTransactionRequest {
    private String email;
    private BigDecimal amount;



}
