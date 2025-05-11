package com.example.service;


import java.util.List;

import com.example.model.Book;
import com.example.exceptions.BookNotFound;

public interface BookService {
	public abstract String saveBook(Book book); // Adds a new book

	public abstract Book updateBook(Book book); // Updates an existing book
	
	public boolean existsByBookIdAndReturnedFalse(int bookId);
	
	public abstract Book getBook(int bookId) throws BookNotFound; // Retrieves a book by ID

	public abstract List<Book> getAllBooks(); // Fetches all books

	public abstract String deleteBook(int bookId); // Removes a book

}