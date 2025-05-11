package com.example.demo;

import com.example.client.OverdueFinesClient;
import com.example.exceptions.TransactionNotFoundException;
import com.example.exceptions.BorrowingLimitExceededException;
import com.example.model.BookTransaction;
import com.example.repository.BookTransactionRepository;
import com.example.service.BookTransactionServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookTransactionServiceImplTest {

    @Mock
    private BookTransactionRepository repository;

    @Mock
    private OverdueFinesClient overdueFinesClient;

    @InjectMocks
    private BookTransactionServiceImpl transactionService;

    private BookTransaction sampleTransaction;

    @BeforeEach
    void setUp() {
        sampleTransaction = new BookTransaction();
        sampleTransaction.setTransactionId(1);
        sampleTransaction.setMemberId(100);
        sampleTransaction.setBookId(200);
        sampleTransaction.setBorrowDate(LocalDate.now());
        sampleTransaction.setDueDate(LocalDate.now().plusDays(14));
        sampleTransaction.setStatus("Borrowed");
    }

    @Test
    void testBorrowBook_Success() {
        when(repository.countBorrowedBooks(100)).thenReturn(5L);
        when(repository.countByBookIdAndStatus(200, "Borrowed")).thenReturn((long) 0);
        when(repository.save(any(BookTransaction.class))).thenReturn(sampleTransaction);

        BookTransaction transaction = transactionService.borrowBook(sampleTransaction);

        assertNotNull(transaction);
        assertEquals("Borrowed", transaction.getStatus());
        verify(repository, times(1)).save(any(BookTransaction.class));
    }

    @Test
    void testBorrowBook_BorrowingLimitExceeded() {
        when(repository.countBorrowedBooks(100)).thenReturn(10L);

        assertThrows(BorrowingLimitExceededException.class, () -> transactionService.borrowBook(sampleTransaction));
    }

    @Test
    void testReturnBook_Success() throws TransactionNotFoundException {
        sampleTransaction.setReturnDate(LocalDate.now());
        when(repository.findById(1)).thenReturn(Optional.of(sampleTransaction));

        String result = transactionService.returnBook(1);

        assertEquals("Book returned successfully!", result);
        verify(repository, times(1)).save(sampleTransaction);
    }

    @Test
    void testReturnBook_OverdueFineApplied() throws TransactionNotFoundException {
        sampleTransaction.setDueDate(LocalDate.now().minusDays(5));
        sampleTransaction.setReturnDate(LocalDate.now());
        when(repository.findById(1)).thenReturn(Optional.of(sampleTransaction));
        when(overdueFinesClient.generateFine(1)).thenReturn(99);

        String result = transactionService.returnBook(1);

        assertEquals("Book returned late. Fine ID generated: 99", result);
        assertEquals("Overdue", sampleTransaction.getStatus());
        verify(repository, times(1)).save(sampleTransaction);
    }

    @Test
    void testReturnBook_TransactionNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.returnBook(1));
    }

    @Test
    void testValidateBookAvailability_True() {
        when(repository.countByBookIdAndStatus(200, "Borrowed")).thenReturn((long) 0);

        assertTrue(transactionService.validateBookAvailability(200));
    }

    @Test
    void testValidateBookAvailability_False() {
        when(repository.countByBookIdAndStatus(200, "Borrowed")).thenReturn((long) 1);

        assertFalse(transactionService.validateBookAvailability(200));
    }

    @Test
    void testExtendLoanPeriod_Success() throws TransactionNotFoundException {
        when(repository.findById(1)).thenReturn(Optional.of(sampleTransaction));

        String result = transactionService.extendLoanPeriod(1, 7);

        assertEquals("Loan period extended successfully!", result);
        assertEquals(LocalDate.now().plusDays(21), sampleTransaction.getDueDate());
        verify(repository, times(1)).save(sampleTransaction);
    }

    @Test
    void testExtendLoanPeriod_TransactionNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.extendLoanPeriod(1, 7));
    }
}
