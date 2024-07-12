package com.gosave.gosave.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class WalletResponse {
    private Long id;
    private BigDecimal balance;
}
