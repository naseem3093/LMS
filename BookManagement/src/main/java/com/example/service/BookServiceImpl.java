package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.exceptions.BookNotFound;
import com.example.model.Book;
import com.example.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository repository;

	@Override
	public String saveBook(Book book) {
		repository.save(book);
		return "Book saved successfully";
	}

	@Override
	public Book updateBook(Book book) {
		return repository.save(book);
	}

	@Override
	public Book getBook(int bookId) throws BookNotFound {
		Optional<Book> optional = repository.findById(bookId);
		if (optional.isPresent())
			return optional.get();
		else
			throw new BookNotFound("Book ID is not valid");
	}

	@Override
	public List<Book> getAllBooks() {
		return repository.findAll();
	}

	@Override
	public String deleteBook(int bookId) {

		repository.deleteById(bookId);
		return "Book deleted successfully!";
	}

}
