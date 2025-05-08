package com.example.client; 

import com.example.dto.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-management", url = "http://localhost:8081")
public interface BookClient {
	@GetMapping("/books/{bookId}")
	BookDTO getBookById(@PathVariable int bookId);
}
