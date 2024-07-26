package com.gosave.gosave.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private BigDecimal amount;
    private String firstName;
    private String lastName;
    @OneToOne
    private Wallet wallet;


}
