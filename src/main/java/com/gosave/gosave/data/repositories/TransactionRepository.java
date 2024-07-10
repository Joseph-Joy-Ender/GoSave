package com.gosave.gosave.data.repositories;

import com.gosave.gosave.data.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
