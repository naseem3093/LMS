package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.BookNotFound;
import com.example.model.Book;
import com.example.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	BookService service;

	@PostMapping("/save")
	public String saveBook(@RequestBody Book book) {
		return service.saveBook(book);
	}

	@PutMapping("/update")
	public Book updateBook(@RequestBody Book book) {
		return service.updateBook(book);
	}

	@GetMapping("/fetchById/{id}")
	public Book getBook(@PathVariable("id") int bookId) throws BookNotFound {
		return service.getBook(bookId);
	}

	@GetMapping("/fetchAll")
	public List<Book> getAllBooks() {
		return service.getAllBooks();
	}

	@DeleteMapping("delete/{id}")
	public String deleteBook(@PathVariable("id") int bookId) {
		return service.deleteBook(bookId);
	}
}
