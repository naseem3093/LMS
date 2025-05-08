package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.example.model.BookTransaction;

import feign.Param;

@Repository
public interface BookTransactionRepository extends JpaRepository<BookTransaction, Integer> {
	// Count books borrowed by a member (to enforce borrowing limit)
	long countByMemberIdAndStatus(int memberId, String status);

	boolean existsByBookIdAndStatus(int bookId, String status); // Checks if book is borrowed

	// Check if a book is currently borrowed
	long countByBookIdAndStatus(int bookId, String status);

	int findByTransactionId(int transactionId);

	// Fetch overdue transactions
	List<BookTransaction> findByStatus(String status);

	@Query("SELECT COUNT(t) FROM BookTransaction t WHERE t.memberId = :memberId AND t.status = 'Borrowed'")
	long countBorrowedBooks(@Param("memberId") int memberId);
}
