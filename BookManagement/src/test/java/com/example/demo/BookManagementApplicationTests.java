package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.exceptions.BookNotFound;
import com.example.model.Book;
import com.example.repository.BookRepository;
import com.example.service.BookServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookManagementApplicationTests {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // This test ensures that the Spring Boot application context loads successfully.
    @Test
    void contextLoads() {
        // The context load test remains here as a sanity check.
    }

    @Test
    public void testSaveBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("John Doe");

        when(repository.save(any(Book.class))).thenReturn(book);

        String result = bookService.saveBook(book);
        assertEquals("Book saved successfully", result);
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book();
        book.setBookId(1);
        book.setTitle("Updated Title");
        book.setAuthor("Jane Doe");

        when(repository.save(any(Book.class))).thenReturn(book);

        Book updated = bookService.updateBook(book);
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Jane Doe", updated.getAuthor());
    }

    @Test
    public void testGetBookSuccess() throws BookNotFound {
        Book book = new Book();
        book.setBookId(1);
        book.setTitle("Test Book");
        book.setAuthor("John Doe");

        when(repository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.getBook(1);
        assertNotNull(result);
        assertEquals(1, result.getBookId());
    }

    @Test
    public void testGetBookFailure() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFound.class, () -> {
            bookService.getBook(1);
        });

        String expectedMessage = "Book ID is not valid";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> books = Arrays.asList(book1, book2);

        when(repository.findAll()).thenReturn(books);
        List<Book> result = bookService.getAllBooks();
        assertEquals(2, result.size());
    }

    @Test
    public void testDeleteBook() {
        // When deletion is successful, the result string should be as expected.
        String result = bookService.deleteBook(1);
        verify(repository, times(1)).deleteById(1);
        assertEquals("Book deleted successfully!", result);
    }

    @Test
    public void testExistsByBookIdAndReturnedFalse() {
        // The method currently returns false as a placeholder.
        boolean exists = bookService.existsByBookIdAndReturnedFalse(1);
        assertFalse(exists);
    }
}
