package com.gosave.gosave.dto.request;

import com.gosave.gosave.data.model.Duration;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private BigDecimal amount;
    private Duration  duration;
}
