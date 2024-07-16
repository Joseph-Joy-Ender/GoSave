package com.gosave.gosave.data.model;

import jakarta.persistence.*;
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
    private Duration duration;
    private BigDecimal balance;
    private BigDecimal amount;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
