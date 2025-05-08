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

//
//
//Alright! Here are the updated Postman request URLs and JSON payloads for the Library Management System, replacing "department" with "books":
//
//1. Save a Book (POST)
//URL: http://localhost:8081/books/save JSON:
//
//json
//{
//    "bookId": 101,
//    "title": "The Great Gatsby",
//    "author": "F. Scott Fitzgerald",
//    "genre": "Classic"
//}
//2. Update a Book (PUT)
//URL: http://localhost:8081/books/update JSON:
//
//json
//{
//    "bookId": 101,
//    "title": "The Great Gatsby - Revised Edition",
//    "author": "F. Scott Fitzgerald",
//    "genre": "Classic"
//}
//3. Fetch a Book by ID (GET)
//URL: http://localhost:8081/books/fetchById/101
//
//4. Fetch All Books (GET)
//URL: http://localhost:8081/books/fetchAll
//
//5. Delete a Book (DELETE)
//URL: http://localhost:8081/books/delete/101
