package com.gosave.gosave.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
public class Wallet {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private BigDecimal balance;

    @OneToMany
    private List<Transaction> transaction;

}
