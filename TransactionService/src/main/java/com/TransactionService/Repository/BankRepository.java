package com.TransactionService.Repository;

import com.TransactionService.Model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    Optional<Bank> findByNameIgnoreCase(String name);
    Optional<Bank> findFirstBySlug(String slug);
    Optional<Bank> findFirstByCode(String code);
    List<Bank> findByActive(Boolean isActive);
    List<Bank> findByIsDeleted(Boolean isDeleted);
    List<Bank> findByActiveTrueAndIsDeletedFalse();
}