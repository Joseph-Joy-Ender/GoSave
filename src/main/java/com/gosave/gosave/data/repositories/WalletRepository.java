package com.gosave.gosave.data.repositories;

import com.gosave.gosave.data.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
