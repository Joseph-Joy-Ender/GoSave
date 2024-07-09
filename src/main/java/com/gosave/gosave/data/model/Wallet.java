package com.gosave.gosave.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
public class Wallet {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private BigDecimal balance;
    private Duration duration;
    private TimePeriod timePeriod;
    private BigDecimal amount;
}
