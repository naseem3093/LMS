package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.client.OverdueFinesClient;
import com.example.exceptions.TransactionNotFoundException;
import com.example.model.BookTransaction;
import com.example.repository.BookTransactionRepository;

@Service
public class BookTransactionServiceImpl implements BookTransactionService {
	  
	@Autowired
	private BookTransactionRepository repository;
	

    @Autowired
    private OverdueFinesClient overdueFinesClient;

	@Override
	public BookTransaction borrowBook(BookTransaction transaction) {
		// Validate Borrowing Limit (max 10 books per member)
		long borrowedBooks = repository.countBorrowedBooks(transaction.getMemberId()); // ✅ Correct method call
		if (borrowedBooks >= 10) {
			throw new IllegalArgumentException("Borrowing limit reached (10 books max).");
		}

		// Proceed with borrowing logic...
		transaction.setBorrowDate(LocalDate.now());
		transaction.setDueDate(LocalDate.now().plusDays(14));
		transaction.setStatus("Borrowed");

		return repository.save(transaction);
	}

	@Override
	public String returnBook(int transactionId) throws TransactionNotFoundException {
	    Optional<BookTransaction> optional = repository.findById(transactionId);
	    if (optional.isPresent()) {
	        BookTransaction transaction = optional.get();
	        transaction.setReturnDate(LocalDate.now());

	        if (transaction.getReturnDate().isAfter(transaction.getDueDate())) {
	            int fineId = overdueFinesClient.generateFine(transactionId);  // ✅ Call Overdue & Fines microservice

	            transaction.setStatus("Overdue");
	            transaction.setFineId(fineId);  // ✅ Directly store Fine ID in BookTransaction

	            repository.save(transaction);
	            return "Book returned late. Fine ID generated: " + fineId;
	        } else {
	            transaction.setStatus("Returned");
	            repository.save(transaction);
	            return "Book returned successfully!";
	        }
	    } else {
	        throw new TransactionNotFoundException("Transaction ID not found.");
	    }
	}




	@Override
	public boolean validateBookAvailability(int bookId) {
		return repository.countByBookIdAndStatus(bookId, "Borrowed") == 0; // Available if no active loan
	}

	@Override
	public List<BookTransaction> trackOverdueBooks() {
		return repository.findByStatus("Overdue");
	}

	@Override
	public String extendLoanPeriod(int transactionId, int extraDays) throws TransactionNotFoundException {
		Optional<BookTransaction> optional = repository.findById(transactionId);
		if (optional.isPresent()) {
			BookTransaction transaction = optional.get();
			transaction.setDueDate(transaction.getDueDate().plusDays(extraDays));
			repository.save(transaction);
			return "Loan period extended successfully!";
		} else {
			throw new TransactionNotFoundException("Transaction ID not found.");
		}
	}

	@Override
	public String borrowBook(int memberId, int bookId) {
		// TODO Auto-generated method stub
		return null;
	}
}
