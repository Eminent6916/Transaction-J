package com.TransactionService.Repository;

import com.TransactionService.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByVerificationToken(String token);

    Optional<User> findByEmail(String email);
}
