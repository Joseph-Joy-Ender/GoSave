package com.gosave.gosave.data.repositories;

import com.gosave.gosave.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
