package com.example.service;

import java.util.List;

import com.example.exceptions.TransactionNotFoundException;
import com.example.model.BookTransaction;

public interface BookTransactionService {
	
	public abstract BookTransaction borrowBook(BookTransaction transaction);

	public abstract String returnBook(int transactionId) throws TransactionNotFoundException; // Process book return

	public abstract boolean validateBookAvailability(int bookId); // Check if book is available

	public abstract List<BookTransaction> trackOverdueBooks(); // Find overdue transactions

	public abstract String extendLoanPeriod(int transactionId, int extraDays) throws TransactionNotFoundException; // Extend
																													// loan																										// duratio
	public abstract String borrowBook(int memberId, int bookId);
}
