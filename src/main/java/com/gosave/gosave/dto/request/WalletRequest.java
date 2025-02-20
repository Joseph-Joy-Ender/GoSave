package com.gosave.gosave.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletRequest {
    private Long id;
    private BigDecimal balance;
}
