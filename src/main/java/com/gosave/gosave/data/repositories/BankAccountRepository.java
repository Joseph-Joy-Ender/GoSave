package com.gosave.gosave.data.repositories;

import com.gosave.gosave.data.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
