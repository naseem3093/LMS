package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.exceptions.TransactionNotFoundException;
import com.example.model.BookTransaction;
import com.example.service.BookTransactionService;

@RestController
@RequestMapping("/transactions")
public class BookTransactionController {
	@Autowired
	private BookTransactionService service;

	@PostMapping("/borrow")
	public BookTransaction borrowBook(@RequestBody BookTransaction transaction) {
		return service.borrowBook(transaction);
	}

	@PatchMapping("/return/{transactionId}")
	public String returnBook(@PathVariable("transactionId") int transactionId) throws TransactionNotFoundException {
		return service.returnBook(transactionId);
	}

	@GetMapping("/validateAvailability/{bookId}")
	public boolean validateBookAvailability(@PathVariable("bookId") int bookId) {
		return service.validateBookAvailability(bookId);
	}

	@GetMapping("/overdue")
	public List<BookTransaction> trackOverdueBooks() {
		return service.trackOverdueBooks();
	}

	@PatchMapping("/extend/{transactionId}/{extraDays}")
	public String extendLoanPeriod(@PathVariable("transactionId") int transactionId,
			@PathVariable("extraDays") int extraDays) throws TransactionNotFoundException {
		return service.extendLoanPeriod(transactionId, extraDays);
	}
}
