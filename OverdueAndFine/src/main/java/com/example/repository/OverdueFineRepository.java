package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.model.OverdueFine;

public interface OverdueFineRepository extends JpaRepository<OverdueFine, Integer> {
    
    // Find fine by transaction ID
    Optional<OverdueFine> findByTransactionId(int transactionId);

    // Retrieve pending fines for payment tracking
    List<OverdueFine> findByFineStatus(String fineStatus);

    // ✅ Custom JPQL Query for Overdue Transactions
    @Query("SELECT f FROM OverdueFine f WHERE f.fineIssuedDate < :currentDate")
    List<OverdueFine> findOverdueTransactions(@Param("currentDate") LocalDate currentDate);
}
